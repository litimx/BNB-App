package learn.masteryweek.data;

import learn.masteryweek.models.Guest;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class GuestFileRepository implements GuestRepository {

    private final String filePath;

    public GuestFileRepository(String filePath) {
        this.filePath = filePath;
    }

    //may not have to use findguestbyid - stretch goal
    @Override
    public Guest findGuestByID(int id) {
        List<Guest> allGuests = findAllGuests();
        for (Guest guest : allGuests) {
            if (guest.getGuestId() == id) {
                return guest;
            }
        }
        return null;
    }

    @Override
    public Guest findByEmail(String guestEmail) {
        List<Guest> allGuests = findAllGuests();
        for (Guest guest : allGuests) {
            if (guest.getEmail().equalsIgnoreCase(guestEmail)) {
                return guest;
            }
        }
        return null;
    }

    @Override
    public List<Guest> findAllGuests() {
        List<Guest> guests = new ArrayList<>();
        File file = new File(filePath);
        try (Scanner scanner = new Scanner(file)) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String[] guestData = scanner.nextLine().split(",");
                Guest guest = deserializeGuest(guestData);
                guests.add(guest);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return guests;
    }

    private Guest deserializeGuest(String[] fields) {
        Guest result = new Guest();
        result.setGuestId(Integer.parseInt(fields[0]));
        result.setFirstName(fields[1]);
        result.setLastName(fields[2]);
        result.setEmail(fields[3]);
        result.setPhone(fields[4]);
        result.setState(fields[5]);
        return result;
    }
}
