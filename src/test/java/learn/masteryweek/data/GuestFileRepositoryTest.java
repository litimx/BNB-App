package learn.masteryweek.data;

import learn.masteryweek.models.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class GuestFileRepositoryTest {

    static final String SEED_PATH = "./data/testfiles/guests-seed.csv";
    static final String TEST_PATH = "./data/testfiles/guests-test.csv";
    static final int NEXT_ID = 27;

    GuestFileRepository repository;

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_PATH);
        Path testPath = Paths.get(TEST_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);

        repository = new GuestFileRepository(TEST_PATH);
    }
    @Test
    public void shouldFindGuestById() {
        Guest guest = repository.findGuestByID(1);
        assertNotNull(guest);
        assertEquals(1, guest.getGuestId());
    }

    @Test
    public void shouldFindByEmail() {
        Guest guest = repository.findByEmail("abc@email.com");
        assertNotNull(guest);
        assertEquals("abc@email.com", guest.getEmail());
    }

    @Test
    public void shouldFindAllGuests() {
        List<Guest> guests = repository.findAllGuests();
        assertNotNull(guests);
        assertTrue(guests.size() > 0);
    }
}
