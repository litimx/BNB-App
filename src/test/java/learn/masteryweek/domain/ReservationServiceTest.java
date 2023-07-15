package learn.masteryweek.domain;

import learn.masteryweek.data.DataException;
import learn.masteryweek.models.Reservation;
import learn.masteryweek.models.Host;
import learn.masteryweek.models.Guest;
import learn.masteryweek.data.ReservationRepositoryDouble;
import learn.masteryweek.data.HostRepositoryDouble;
import learn.masteryweek.data.GuestRepositoryDouble;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationServiceTest {
    ReservationService service;
    HostRepositoryDouble hostRepoDouble;
    GuestRepositoryDouble guestRepoDouble;

    @BeforeEach
    void setup() {
        hostRepoDouble = new HostRepositoryDouble();
        guestRepoDouble = new GuestRepositoryDouble();
        service = new ReservationService(new ReservationRepositoryDouble(), hostRepoDouble, guestRepoDouble);
    }

    @Test
    void shouldFindByHost() throws DataException {
        List<Reservation> reservations = service.viewReservationsByHost(hostRepoDouble.HOST.getEmail());
        assertTrue(!reservations.isEmpty());
    }

    @Test
    void shouldAdd() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(hostRepoDouble.HOST);
        reservation.setGuest(guestRepoDouble.GUEST);
        reservation.setStartDate(LocalDate.now().plusDays(3));
        reservation.setEndDate(LocalDate.now().plusDays(10));

        Result<Reservation> result = service.add(reservation);

        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals(3, result.getPayload().getReservationId());
    }

    @Test
    void shouldNotAddNull() {
        assertThrows(DataException.class, () -> service.add(null));
    }

    @Test
    void shouldNotAddNullHost() {
        Reservation reservation = new Reservation();
        reservation.setGuest(guestRepoDouble.GUEST);
        reservation.setStartDate(LocalDate.now().plusDays(30));
        reservation.setEndDate(LocalDate.now().plusDays(40));

        assertThrows(DataException.class, () -> service.add(reservation));
    }
}
