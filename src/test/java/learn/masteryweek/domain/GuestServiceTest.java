package learn.masteryweek.domain;

import learn.masteryweek.data.GuestRepositoryDouble;
import learn.masteryweek.models.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestServiceTest {
    GuestService service;

    @BeforeEach
    void setup() {
        service = new GuestService(new GuestRepositoryDouble());
    }

    @Test
    void shouldFindAll() {
        List<Guest> guests = service.findAll();
        assertNotNull(guests);
    }

    @Test
    void shouldFindByEmail() {
        String testEmail = "ex2@test.com";
        Guest guest = service.findByEmail(testEmail);
        assertNotNull(guest);
        assertEquals(testEmail, guest.getEmail());
    }

    @Test
    void shouldNotFindRandomEmail() {
        String unknownEmail = "random12345@testtest.com";
        Guest guest = service.findByEmail(unknownEmail);
        assertNull(guest);
    }


    @Test
    void shouldNotFindUnknownId() {
        int unknownID = 9999999;
        Guest guest = service.findGuestByID(unknownID);
        assertNull(guest);
    }
}