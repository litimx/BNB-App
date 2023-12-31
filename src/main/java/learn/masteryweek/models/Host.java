package learn.masteryweek.models;

import java.math.BigDecimal;
import java.util.UUID;

public class Host {
    private UUID hostid;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String postalCode;
    private BigDecimal standardRate;
    private BigDecimal weekendRate;


    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public BigDecimal getStandardRate() {
        return standardRate;
    }
    public void setStandardRate(BigDecimal standardRate) {
        this.standardRate = standardRate;
    }
    public BigDecimal getWeekendRate() {
        return weekendRate;
    }
    public void setWeekendRate(BigDecimal weekendRate) {
        this.weekendRate = weekendRate;
    }
    public UUID getHostId() {
        return hostid;
    }
    public void setHostId(UUID hostId) {
        this.hostid = hostId;
    }
}
