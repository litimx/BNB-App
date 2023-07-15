package learn.masteryweek.ui;

import learn.masteryweek.data.DataException;
import learn.masteryweek.domain.Result;
import learn.masteryweek.models.Guest;
import learn.masteryweek.models.Host;
import learn.masteryweek.models.Reservation;
import learn.masteryweek.domain.HostService;
import learn.masteryweek.domain.GuestService;
import learn.masteryweek.domain.ReservationService;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Comparator;

public class View {

    private final ConsoleIO io;
    private final HostService hostService;
    private final GuestService guestService;
    private final ReservationService reservationService;

    public View(ConsoleIO io, HostService hostService, GuestService guestService, ReservationService reservationService) {
        this.io = io;
        this.hostService = hostService;
        this.guestService = guestService;
        this.reservationService = reservationService;
    }

    public MainMenuOption selectMainMenuOption() {
        displayHeader("---Main Menu---");
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (MainMenuOption option : MainMenuOption.values()) {
            if (!option.isHidden()) {
                io.printf("%s. %s%n", option.getValue(), option.getMessage());
            }
            min = Math.min(min, option.getValue());
            max = Math.max(max, option.getValue());
        }
        String message = String.format("Select [%s-%s]: ", min, max);
        return MainMenuOption.fromValue(io.readInt(message, min, max));
    }

    public void displayHeader(String message) {
        io.println("");
        io.println(message);
        io.println("=".repeat(message.length()));
    }

    public Reservation getNewReservation() throws DataException {
        String hostEmail = io.readRequiredString("Enter the host's email: ");
        String guestEmail = io.readRequiredString("Enter the guest's email: ");

        List<Reservation> existingReservations;
        try {
            existingReservations = reservationService.viewReservationsByHost(hostEmail);
        } catch (DataException e) {
            io.println("Error retrieving reservations for the host");
            return null;
        }


        if (!existingReservations.isEmpty()) {
            existingReservations.sort(Comparator.comparing(Reservation::getStartDate));

            io.println("Existing reservations for this host: ");
            for (Reservation reservation : existingReservations) {
                io.printf("Reservation ID: %s, Start date: %s, End date: %s, Total: %s, Guest: %s %s, Email: %s%n",
                        reservation.getReservationId(),
                        reservation.getStartDate(),
                        reservation.getEndDate(),
                        reservation.getTotal(),
                        reservation.getGuest().getFirstName(),
                        reservation.getGuest().getLastName(),
                        reservation.getGuest().getEmail());
            }
        } else {
            io.println("No existing reservations for this host.");
        }

        LocalDate startDate = io.readLocalDate("Enter the reservation start date (MM/DD/YYYY): ");
        LocalDate endDate = io.readLocalDate("Enter the reservation end date (MM/DD/YYYY): ");

        Guest guest = guestService.findByEmail(guestEmail);
        Host host = hostService.findHostByEmail(hostEmail);

        boolean hostExists = true, guestExists = true;

        if(guest == null) {
            throw new DataException("Guest was not found.");
        }
        if(host == null) {
            throw new DataException("Host was not found.");
        }

        if(!guestExists || !hostExists) {
            return null;
        }

        Reservation reservation = new Reservation();
        reservation.setGuest(guest);
        reservation.setHost(host);
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);

        //calculate cost of reservation

        Result<Reservation> result = reservationService.add(reservation);
        if(!result.isSuccess()) {
            // Handle error messages if any
            for(String message : result.getErrorMessages()) {
                io.println(message);
            }
            return null;
        }

        reservation = result.getPayload();

        // Display reservation summary and ask for confirmation
        io.printf("Reservation summary: %n Start date: %s, End date: %s, Total cost: %s%n",
                startDate, endDate, reservation.getTotal());
        String confirm = io.readRequiredString("Do you want to confirm this reservation? Enter 'Y' for Yes and 'N' for No: ");
        if (!confirm.equalsIgnoreCase("Y")) {
            io.println("Reservation not confirmed. Returning to main menu.");
            return null;
        }

        return reservation;
    }
    public Reservation getUpdatedReservation(String hostEmail) {

        List<Reservation> reservations;
        try {
            reservations = reservationService.viewReservationsByHost(hostEmail);
        } catch (DataException e) {
            io.println("Error retrieving reservations");
            return null;
        }

        if(reservations.isEmpty()) {
            io.println("No reservations found for this host.");
            return null;
        }

        io.println("Found " + reservations.size() + " reservation(s) for this email:");
        int index = 1;
        for(Reservation reservation : reservations) {
            Guest guest = reservation.getGuest();
            io.printf("%d: Guest: %s %s, Start date: %s, End date: %s%n",
                    index,
                    guest.getFirstName(),
                    guest.getLastName(),
                    reservation.getStartDate(),
                    reservation.getEndDate());
            index++;
        }

        int selection;
        do {
            String selectionStr = io.readRequiredString("Enter which reservation you would like updated.");
            try {
                selection = Integer.parseInt(selectionStr);
            } catch (NumberFormatException e) {
                io.println("Invalid input. Please enter a number.");
                selection = 0;
            }
        } while (selection < 1 || selection > reservations.size());

        Reservation reservation = reservations.get(selection - 1);

        LocalDate startDate = io.readLocalDate("Enter the new start date (MM/DD/YYYY): ");
        LocalDate endDate = io.readLocalDate("Enter the new end date (MM/DD/YYYY): ");

        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);

        //calculating updated total cost

        BigDecimal total = calculateTotal(reservation);
        reservation.setTotal(total);


        io.printf("Updated Reservation: %n Start date: %s, End date: %s, Total cost: %s%n",
                startDate, endDate, reservation.getTotal());
        String confirm = io.readRequiredString("Do you want to confirm this update? Enter 'Y' for Yes or 'N' for No: ");
        if (!confirm.equalsIgnoreCase("Y")) {
            io.println("Reservation update not confirmed. Returning to main menu.");
            return null;
        }

        return reservation;
    }

    private BigDecimal calculateTotal(Reservation reservation) {
        BigDecimal total = BigDecimal.ZERO;
        for (LocalDate date = reservation.getStartDate(); date.isBefore(reservation.getEndDate()); date = date.plusDays(1)) {
            if (date.getDayOfWeek() == DayOfWeek.FRIDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY) {
                total = total.add(reservation.getHost().getWeekendRate());
            } else {
                total = total.add(reservation.getHost().getStandardRate());
            }
        }
        return total;
    }
    public String getHostEmail() {

        return io.readRequiredString("Please enter host's e-mail address.");
    }
    public void displayReservations(List<Reservation> reservations) {
        reservations.sort(Comparator.comparing(Reservation::getStartDate));
        for (Reservation reservation : reservations) {
            io.println("");
            io.printf("Reservation ID: %s%n", reservation.getReservationId());
            io.println("-".repeat(20));
            io.printf("Start date: %s%n", reservation.getStartDate());
            io.println("-".repeat(20));
            io.printf("End date: %s%n", reservation.getEndDate());
            io.println("-".repeat(20));
            io.printf("Total: %s%n", reservation.getTotal());
            io.println("-".repeat(20));

            if(reservation.getGuest() != null) {
                io.printf("Guest: %s %s, Email: %s%n", reservation.getGuest().getFirstName(),
                        reservation.getGuest().getLastName(), reservation.getGuest().getEmail());
                io.println("-".repeat(20));
            } else {
                io.printf("Guest: %s%n", "null");
            }
            if(reservation.getHost() != null) {
                io.printf("Host: %s, Email: %s%n", reservation.getHost().getLastName(),
                        reservation.getHost().getEmail());
                io.println("-".repeat(20));
            } else {
                io.printf("Host: %s%n", "null");
            }
        }
    }
    public void displaySuccess(String message) {
        io.println("Success!");
    }
    public void displayErrors(List<String> errors) {
        io.println("Error");
        for (String error : errors) {
            io.println(error);
        }
    }
    public void displayError(String message) {
        io.println("Error: " + message);
    }

    public Reservation getReservationToDelete(ReservationService service) {
        String hostEmail = io.readRequiredString("Please enter host's e-mail.");

        List<Reservation> reservations = null;
        try {
            reservations = service.viewReservationsByHost(hostEmail);
        } catch (DataException ex) {
            displayError("No reservations found for this host.");
        }

        if (reservations == null || reservations.isEmpty()) {
            return null;
        }

        io.println("Reservations for host: ");
        int counter = 1;
        for (Reservation reservation : reservations) {
            io.println(counter + ": " + reservation.toString());
            counter++;
        }

        int reservationSelection = io.readInt("Select a reservation to delete: ", 1, counter - 1);
        return reservations.get(reservationSelection - 1);
    }

    public void displayHosts(Host host) {
        io.println("Host Information:");
        io.println("Host ID: " + host.getHostId());
        io.println("Last Name: " + host.getLastName());
        io.println("Email: " + host.getEmail());
        io.println("Phone: " + host.getPhone());
        io.println("Address: " + host.getAddress());
        io.println("City: " + host.getCity());
        io.println("State: " + host.getState());
        io.println("Postal Code: " + host.getPostalCode());
        io.println("Standard Rate: " + host.getStandardRate());
        io.println("Weekend Rate: " + host.getWeekendRate());
    }
    public void displayGuests(Guest guest) {
        io.println("Guest Information:");
        io.println("Guest ID: " + guest.getGuestId());
        io.println("First Name: " + guest.getFirstName());
        io.println("Last Name: " + guest.getLastName());
        io.println("Email: " + guest.getEmail());
        io.println("Phone: " + guest.getPhone());
        io.println("State: " + guest.getState());
    }
    public String getGuestEmail() {
        return io.readRequiredString("Please enter guest's e-mail.");
    }
}

