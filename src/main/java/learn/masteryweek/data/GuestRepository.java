package learn.masteryweek.data;
import learn.masteryweek.models.Guest;
import java.util.List;
public interface GuestRepository {
    Guest findGuestByID(int guestID);
    Guest findByEmail(String guestEmail);
    List<Guest> findAllGuests();
}
