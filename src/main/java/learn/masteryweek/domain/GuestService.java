package learn.masteryweek.domain;

import learn.masteryweek.data.GuestRepository;
import learn.masteryweek.models.Guest;
import java.util.List;

public class GuestService {
    private final GuestRepository repository;

    public GuestService(GuestRepository repository) {
        this.repository = repository;
    }

    public List<Guest> findAll() {
        return repository.findAllGuests();
    }

    public Guest findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Guest findGuestByID(int guestID) {
        return repository.findGuestByID(guestID);
    }
}

