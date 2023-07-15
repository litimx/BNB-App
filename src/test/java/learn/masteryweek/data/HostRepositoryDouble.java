package learn.masteryweek.data;

import learn.masteryweek.models.Host;

import java.math.BigDecimal;
import java.util.*;

public class HostRepositoryDouble implements HostRepository {

    public final Host HOST = makeHost();

    private Host makeHost() {
        Host host = new Host();
        host.setHostId(UUID.fromString("3edda6b8-782e-459e-8e81-7a781daf68e1"));
        host.setLastName("Williams");
        host.setEmail("abc@test.com");
        host.setPhone("(555) 555-5555");
        host.setAddress("123 Red Sky Road");
        host.setCity("Williamnopolis");
        host.setState("HI");
        host.setPostalCode("55555");
        host.setStandardRate(BigDecimal.valueOf(100));
        host.setWeekendRate(BigDecimal.valueOf(150));
        return host;
    }

    @Override
    public Host findHostByEmail(String hostEmail) {
        if (HOST.getEmail().equalsIgnoreCase(hostEmail)) {
            return HOST;
        }
        return null;
    }

    @Override
    public Host findHostByFile(String fileName) {
        return null;
    }

    @Override
    public List<Host> findAllHosts() {
        return List.of(HOST);
    }
}