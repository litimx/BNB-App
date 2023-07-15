package learn.masteryweek.data;

import learn.masteryweek.models.Host;
import learn.masteryweek.models.Reservation;
import learn.masteryweek.models.Guest;
import learn.masteryweek.data.DataException;

import java.util.List;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class ReservationFileRepository implements ReservationRepository {

    private final String directoryPath;
    private final String delimiter = ",";
    private final HostFileRepository hostRepository;
    private static final String HEADER = "id,start_date,end_date,guest_id,total";

    public ReservationFileRepository(String directoryPath, HostFileRepository hostRepository) {

        this.directoryPath = directoryPath;
        this.hostRepository = hostRepository;
    }

    private void writeToFile(List<Reservation> reservations, UUID hostId) throws DataException {
        String filePath = getFilePath(hostId);
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println(HEADER);

            if (reservations == null) {
                return;
            }
            for (Reservation reservation : reservations) {
                writer.println(serialize(reservation));
            }
        } catch (FileNotFoundException ex) {
            throw new DataException(ex.getMessage(), ex);
        }
    }

    private String getFilePath(UUID hostId) {

        return Paths.get(directoryPath, hostId + ".csv").toString();
    }

    private String serialize(Reservation reservation){
        return String.format("%s,%s,%s,%s,%s",
                reservation.getReservationId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getGuest().getGuestId(),
                reservation.getTotal());
    }

    //guest object only has ID set, this may create guest with all other fields null.
    private Reservation deserialize (String[] fields) {
        Reservation result = new Reservation ();
        result.setReservationId(Integer.parseInt(fields[0]));
        result.setStartDate(LocalDate.parse(fields[1]));
        result.setEndDate(LocalDate.parse(fields[2]));

        Guest guest = new Guest();
        guest.setGuestId(Integer.parseInt(fields[3]));
        result.setGuest(guest);

        result.setTotal(new BigDecimal(fields[4]));
        return result;
    }

    private List<Reservation> findAll(UUID hostId) throws DataException {
        ArrayList<Reservation> all = new ArrayList<>();
        String fileName = hostId.toString(); // Convert UUID to String to pass to findHostByFile
        Host host = hostRepository.findHostByFile(fileName);
        if (host == null) {
            throw new DataException("Host not found");
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(hostId)))) {
            reader.readLine();
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] fields = line.split(delimiter, -1);
                if (fields.length == 5) {
                    Reservation reservation = deserialize(fields);
                    reservation.setHost(host); // Set the host here
                    all.add(reservation);
                }
            }
        } catch (IOException ex) {
            throw new DataException(ex.getMessage(), ex);
        }
        return all;
    }


    @Override
    public Reservation addReservation(Reservation reservation) throws DataException {
        if (reservation == null) {
            return null;
        }

        List<Reservation> all = findAll(reservation.getHost().getHostId());
        int maxId = all.stream().mapToInt(Reservation::getReservationId).max().orElse(0);

        //new ID for new reservation
        reservation.setReservationId(maxId + 1);

        //add new reservation to file
        all.add(reservation);

        //write back all reservations to file
        writeToFile(all, reservation.getHost().getHostId());
        return reservation;
    }

    @Override
    public boolean editReservation(Reservation reservation) throws DataException {
        if (reservation == null) {
            return false;
        }

        List<Reservation> all = findAll(reservation.getHost().getHostId());
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getReservationId() == reservation.getReservationId()) {
                all.set(i, reservation);
                writeToFile(all, reservation.getHost().getHostId());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteReservation(Reservation reservation) throws DataException {
        if (reservation == null) {
            return false;
        }

        List<Reservation> all = findAll(reservation.getHost().getHostId());
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getReservationId() == reservation.getReservationId()) {
                all.remove(i);
                writeToFile(all, reservation.getHost().getHostId());
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Reservation> viewReservationsByHost(Host host) throws DataException {
        if (host == null) {
            return new ArrayList<>();
        }

        return findAll(host.getHostId());
    }
}
