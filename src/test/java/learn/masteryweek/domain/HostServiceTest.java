package learn.masteryweek.domain;

import learn.masteryweek.data.HostRepositoryDouble;
import learn.masteryweek.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HostServiceTest {
    HostService service;

    @BeforeEach
    void setup() {
        service = new HostService(new HostRepositoryDouble());
    }

    @Test
    void shouldFindAll() {
        assertEquals(1, service.findAll().size());
    }

    @Test
    void shouldFindByEmail() {
        Host host = service.findHostByEmail("abc@test.com");
        assertNotNull(host);
        assertEquals("abc@test.com", host.getEmail());
    }

    @Test
    void shouldNotFindRandomEmail() {
        Host host = service.findHostByEmail("ihavetwodogs@test.com");
        assertNull(host);
    }
}