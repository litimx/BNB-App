package learn.masteryweek.data;
import java.time.LocalDate;
import java.util.List;

import learn.masteryweek.models.Reservation;
import learn.masteryweek.models.Guest;
import learn.masteryweek.models.Host;
import learn.masteryweek.data.DataException;

public interface ReservationRepository {

    Reservation addReservation(Reservation reservation) throws DataException;
    boolean editReservation(Reservation reservation) throws DataException;
    boolean deleteReservation(Reservation reservation) throws DataException;
    List<Reservation> viewReservationsByHost(Host host) throws DataException;
}