package learn.masteryweek.data;

import learn.masteryweek.models.Reservation;
import learn.masteryweek.models.Host;
import learn.masteryweek.models.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/*class ReservationFileRepositoryTest {

    static final String SEED_PATH = "./data/testfiles/reservations-seed.csv";
    static final String TEST_PATH = "./data/testfiles/reservations-test";
    static final UUID TEST_HOST_ID = UUID.fromString("3edda6b8-7811-460c-befa-05f83c4b0752");

    ReservationFileRepository repository = new ReservationFileRepository(TEST_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_PATH);
        Path testPath = Paths.get(TEST_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldAddReservation() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setReservationId(1);
        reservation.setStartDate(LocalDate.now().plusDays(10));
        reservation.setEndDate(LocalDate.now().plusDays(15));
        reservation.setGuest(new Guest());
        reservation.setTotal(new BigDecimal("100.00"));

        Host host = new Host();
        host.setHostId(TEST_HOST_ID);
        reservation.setHost(host);

        Reservation result = repository.addReservation(reservation);

        assertNotNull(result);
        assertEquals(reservation.getReservationId(), result.getReservationId());
        assertEquals(reservation.getStartDate(), result.getStartDate());
        assertEquals(reservation.getEndDate(), result.getEndDate());
    }
 */