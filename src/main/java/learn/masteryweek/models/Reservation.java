package learn.masteryweek.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Reservation {
    private int reservationId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Guest guest;
    private Host host;
    private BigDecimal total;

    public int getReservationId() {
        return reservationId;
    }
    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public Guest getGuest() {
        return guest;
    }
    public void setGuest(Guest guest) {
        this.guest = guest;
    }
    public BigDecimal getTotal() {
        return total;
    }
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    public Host getHost() {
        return host;
    }
    public void setHost(Host host) {
        this.host = host;
    }

    @Override
    public String toString() {
        String guestInfo = (guest != null) ?
                ", Guest: " + guest.getFirstName() + " " + guest.getLastName() +
                        ", Guest Email: " + guest.getEmail() :
                ", Guest information not available";
        return "Reservation ID: " + reservationId +
                ", Start Date: " + startDate +
                ", End Date: " + endDate +
                guestInfo;
    }

}
