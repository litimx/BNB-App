package learn.masteryweek.data;

import learn.masteryweek.models.Reservation;
import learn.masteryweek.models.Host;
import learn.masteryweek.models.Guest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ReservationRepositoryDouble implements ReservationRepository {

    private final ArrayList<Reservation> reservations = new ArrayList<>();
    private int nextId = 1;

    public ReservationRepositoryDouble() {
        Host host1 = new Host();
        host1.setHostId(UUID.randomUUID());
        host1.setLastName("Host1");

        Host host2 = new Host();
        host2.setHostId(UUID.randomUUID());
        host2.setLastName("Host2");

        Guest guest1 = new Guest();
        guest1.setGuestId(1);
        guest1.setLastName("Guest1");

        Guest guest2 = new Guest();
        guest2.setGuestId(2);
        guest2.setLastName("Guest2");

        Reservation reservation1 = new Reservation();
        reservation1.setReservationId(nextId++);
        reservation1.setHost(host1);
        reservation1.setGuest(guest1);
        reservation1.setStartDate(LocalDate.now());
        reservation1.setEndDate(LocalDate.now().plusDays(3));
        reservation1.setTotal(new BigDecimal("600"));

        Reservation reservation2 = new Reservation();
        reservation2.setReservationId(nextId++);
        reservation2.setHost(host2);
        reservation2.setGuest(guest2);
        reservation2.setStartDate(LocalDate.now());
        reservation2.setEndDate(LocalDate.now().plusDays(5));
        reservation2.setTotal(new BigDecimal("1000"));

        reservations.add(reservation1);
        reservations.add(reservation2);
    }

    @Override
    public List<Reservation> viewReservationsByHost(Host host) throws DataException {
        List<Reservation> result = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getHost().getHostId().equals(host.getHostId())) {
                result.add(reservation);
            }
        }
        return result;
    }

    @Override
    public Reservation addReservation(Reservation reservation) throws DataException {
        for (Reservation existing : reservations) {
            if (existing.getReservationId() == reservation.getReservationId()) {
                throw new DataException("The reservation already exists.");
            }
        }
        reservation.setReservationId(nextId++);
        reservations.add(reservation);
        return reservation;
    }

    @Override
    public boolean editReservation(Reservation reservation) throws DataException {
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getReservationId() == reservation.getReservationId()) {
                reservations.set(i, reservation);
                return true;
            }
        }
        throw new DataException("The reservation was not found.");
    }

    @Override
    public boolean deleteReservation(Reservation reservation) throws DataException {
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getReservationId() == reservation.getReservationId()) {
                reservations.remove(i);
                return true;
            }
        }
        throw new DataException("The reservation was not found.");
    }
}