package learn.masteryweek.data;

import learn.masteryweek.models.Guest;

import java.util.*;

public class GuestRepositoryDouble implements GuestRepository {

    public final Guest GUEST = makeGuest();

    private Guest makeGuest() {
        Guest guest = new Guest();
        guest.setGuestId(1);
        guest.setFirstName("Tom");
        guest.setLastName("Test");
        guest.setEmail("ex2@test.com");
        guest.setPhone("(444) 444-4444");
        guest.setState("WA");
        return guest;
    }

    @Override
    public Guest findGuestByID(int id) {
        if (GUEST.getGuestId() == id) {
            return GUEST;
        }
        return null;
    }

    @Override
    public Guest findByEmail(String guestEmail) {
        if (GUEST.getEmail().equalsIgnoreCase(guestEmail)) {
            return GUEST;
        }
        return null;
    }

    @Override
    public List<Guest> findAllGuests() {
        return List.of(GUEST);
    }
}