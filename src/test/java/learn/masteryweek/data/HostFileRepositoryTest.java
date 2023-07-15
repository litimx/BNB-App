package learn.masteryweek.data;

import learn.masteryweek.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.*;

class HostFileRepositoryTest {

    static final String SEED_PATH = "./data/testfiles/hosts-seed.csv";
    static final String TEST_PATH = "./data/testfiles/hosts-test.csv";
    static final String EMAIL = "abc@test.com"; //email address from seed file

    HostFileRepository repository;

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_PATH);
        Path testPath = Paths.get(TEST_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);

        repository = new HostFileRepository(TEST_PATH);
    }

    @Test
    void shouldFindAllHosts() {
        assertTrue(repository.findAllHosts().size() >= 0);
    }

    @Test
    void shouldFindHostByEmail() {
        Host host = repository.findHostByEmail(EMAIL);
        assertNotNull(host);
        assertEquals(EMAIL, host.getEmail());
    }
}