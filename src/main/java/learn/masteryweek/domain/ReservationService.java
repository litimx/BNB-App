package learn.masteryweek.domain;

import learn.masteryweek.data.DataException;
import learn.masteryweek.data.GuestRepository;
import learn.masteryweek.data.HostRepository;
import learn.masteryweek.data.ReservationRepository;
import learn.masteryweek.models.Guest;
import learn.masteryweek.models.Host;
import learn.masteryweek.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final HostRepository hostRepository;
    private final GuestRepository guestRepository;

    public ReservationService(ReservationRepository reservationRepository, HostRepository hostRepository, GuestRepository guestRepository) {
        this.reservationRepository = reservationRepository;
        this.hostRepository = hostRepository;
        this.guestRepository = guestRepository;
    }

    public List<Reservation> viewReservationsByHost(String email) throws DataException {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required.");
        }

        //Find host by email
        Host host = hostRepository.findHostByEmail(email);
        if (host == null) {
            throw new IllegalArgumentException("Host e-mail: " + email + " not found.");
        }

        //return all reservations by host
        List<Reservation> reservations;
        try {
            reservations = reservationRepository.viewReservationsByHost(host);
            //missing here - fetch guest object for each reservation...
            for (Reservation reservation : reservations) {
                Guest guest = guestRepository.findGuestByID(reservation.getGuest().getGuestId());
                reservation.setGuest(guest);
            }
        } catch (DataException e) {
            throw new DataException("There are no reservations for this host.");
        }

        return reservations;
    }

    public Result<Reservation> add(Reservation reservation) throws DataException {
        Result<Reservation> result = validate(reservation);
        if (!result.isSuccess()) {
            return result;
        }

        reservation.setTotal(calculateTotal(reservation));

        reservation = reservationRepository.addReservation(reservation);
        result.setPayload(reservation);

        return result;
    }

    public Result<Reservation> edit(Reservation reservation) throws DataException {
        Result<Reservation> result = validate(reservation);
        if (!result.isSuccess()) {
            return result;
        }

        reservation.setTotal(calculateTotal(reservation));

        boolean success = reservationRepository.editReservation(reservation);
        if (!success) {
            result.addErrorMessage("Could not find reservation.");
        } else {
            result.setPayload(reservation);
        }

        return result;
    }

    public boolean deleteReservation(Reservation reservation) throws DataException {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation is required.");
        }

        if (reservation.getHost() == null) {
            throw new IllegalArgumentException("Host is required.");
        }

        boolean isDeleted = reservationRepository.deleteReservation(reservation);

        if (isDeleted) {
            return true;
        } else {
            return false;
        }
    }

    private Result<Reservation> validate(Reservation reservation) {
        Result<Reservation> result = new Result<>();

        if (reservation.getHost() == null || reservation.getHost().getEmail() == null || reservation.getHost().getEmail().isBlank()) {
            result.addErrorMessage("Valid host email is required.");
        }

        if (reservation.getGuest() == null || reservation.getGuest().getEmail() == null || reservation.getGuest().getEmail().isBlank()) {
            result.addErrorMessage("Valid guest email is required.");
        }
        if (reservation.getStartDate() == null || reservation.getEndDate() == null) {
            result.addErrorMessage("Start and end dates are required.");
        } else if (reservation.getStartDate().isAfter(reservation.getEndDate())) {
            result.addErrorMessage("The start date must be before the end date.");
        } else if (reservation.getStartDate().isBefore(LocalDate.now())) {
            result.addErrorMessage("The start date must be in the future.");
        }


        return result;
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
}
