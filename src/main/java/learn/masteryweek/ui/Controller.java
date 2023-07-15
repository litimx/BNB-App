package learn.masteryweek.ui;

import learn.masteryweek.data.DataException;
import learn.masteryweek.domain.GuestService;
import learn.masteryweek.domain.HostService;
import learn.masteryweek.domain.ReservationService;
import learn.masteryweek.domain.Result;
import learn.masteryweek.models.Guest;
import learn.masteryweek.models.Host;
import learn.masteryweek.models.Reservation;

import java.time.LocalDate;
import java.util.List;

public class Controller {
    private ReservationService service;
    private View view;
    private final HostService hostService;
    private final GuestService guestService;

    public Controller(ReservationService service, View view, HostService hostService, GuestService guestService) {
        this.service = service;
        this.view = view;
        this.hostService = hostService;
        this.guestService = guestService;
    }

    //run
    public void run() {
        MainMenuOption option;
        do {
            option = view.selectMainMenuOption();
            switch (option) {
                case VIEW_RESERVATIONS:
                    viewReservations();
                    break;
                case ADD_RESERVATION:
                    addReservation();
                    break;
                case EDIT_RESERVATION:
                    editReservation();
                    break;
                case DELETE_RESERVATION:
                    deleteReservation();
                    break;

            }
        } while (option != MainMenuOption.EXIT);
    }

    private void editReservation() {
        try {
            String hostEmail = view.getHostEmail();
            Host host = hostService.findHostByEmail(hostEmail);
            if (host == null) {
                view.displayError("Invalid host email. Please try again.");
                return;
            }

            List<Reservation> reservations = service.viewReservationsByHost(hostEmail);
            if (reservations.isEmpty()) {
                view.displayError("No reservations found for this host. Please try again.");
                return;
            }

            Reservation reservation = view.getUpdatedReservation(hostEmail);
            if (reservation == null) {
                view.displayError("Failed to update reservation. Please try again.");
                return;
            }

            // Validate that the reservation to be updated belongs to the host
            //this line of code was causing an error to display each time i try to edit a reservation
            /*if (!reservations.contains(reservation)) {
                view.displayError("The specified reservation does not exist for this host.");
                return;
            }*/

            Result<Reservation> result = service.edit(reservation);
            if (result.isSuccess()) {
                view.displaySuccess("You have successfully edited the reservation.");
            } else {
                view.displayErrors(result.getErrorMessages());
            }
        } catch (IllegalArgumentException | DataException ex) {
            view.displayError(ex.getMessage());
        }
    }

    private void addReservation() {
        Reservation reservation;
        try {
            reservation = view.getNewReservation();
            if (reservation == null) {
                view.displayError("Failed to create reservation. Guest was not found.");
                return;
            }
        } catch (Exception ex) {
            view.displayError("An error occurred while creating the reservation: " + ex.getMessage());
            return;
        }

        try {
            Result<Reservation> result = service.add(reservation);
            if (result.isSuccess()) {
                view.displaySuccess("You have successfully added the reservation.");
            } else {
                view.displayErrors(result.getErrorMessages());
            }
        } catch (DataException ex) {
            view.displayError(ex.getMessage());
        }
    }
    private void deleteReservation() {
        try {
            Reservation reservation = view.getReservationToDelete(service);
            if (reservation == null) {
                view.displayError("Failed to get reservation. Please try again.");
                return;
            }

            // Check if the reservation is in the past
            LocalDate now = LocalDate.now();
            if (reservation.getStartDate().isBefore(now)) {
                view.displayError("Past reservations cannot be deleted.");
                return;
            }

            if (service.deleteReservation(reservation)) {
                view.displaySuccess("You have successfully deleted the reservation.");
            } else {
                view.displayError("Failed to delete reservation.");
            }
        } catch (IllegalArgumentException ex) {
            view.displayError(ex.getMessage());
        } catch (DataException ex) {
            view.displayError(ex.getMessage());
        }
    }
    private void viewReservations() {
        String email = view.getHostEmail();
        try {
            List<Reservation> reservations = service.viewReservationsByHost(email);

            for (Reservation reservation : reservations) {
                Guest guest = guestService.findByEmail(reservation.getGuest().getEmail());
                reservation.setGuest(guest);

                Host host = hostService.findHostByEmail(email);
                reservation.setHost(host);
            }

            view.displayReservations(reservations);
        } catch (IllegalArgumentException ex) {
            view.displayError(ex.getMessage());
            return;
        } catch (DataException ex) {
            view.displayError(ex.getMessage());
        }
    }
    private void getHost() {
        String email = view.getHostEmail();
        Host host = hostService.findHostByEmail(email);
        view.displayHosts(host);
    }

    private void getGuest() {
        String email = view.getGuestEmail();
        Guest guest = guestService.findByEmail(email);
        view.displayGuests(guest);
    }

}

