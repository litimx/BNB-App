package learn.masteryweek.data;

import learn.masteryweek.models.Host;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class HostFileRepository implements HostRepository {
    private final String filePath;

    public HostFileRepository(String filePath) {

        this.filePath = filePath;
    }

    @Override
    public Host findHostByEmail(String hostEmail) {
        List<Host> allHosts = findAllHosts();
        for (Host host : allHosts) {
            if (host.getEmail().equalsIgnoreCase(hostEmail)) {
                return host;
            }
        }
        return null;
    }

    @Override
    public Host findHostByFile(String fileName) {
        List<Host> allHosts = findAllHosts();
        for (Host host : allHosts) {
            if (host.getHostId().equals(UUID.fromString(fileName))) {
                return host;
            }
        }
        return null;
    }

    @Override
    public List<Host> findAllHosts() {
        List<Host> hosts = new ArrayList<>();
        File file = new File(filePath);
        try (Scanner scanner = new Scanner(file)) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String[] hostData = scanner.nextLine().split(",");
                Host host = deserializeHost(hostData);
                hosts.add(host);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return hosts;
    }

    private Host deserializeHost(String[] fields) {
        Host result = new Host();
        result.setHostId(UUID.fromString(fields[0]));
        result.setLastName(fields[1]);
        result.setEmail(fields[2]);
        result.setPhone(fields[3]);
        result.setAddress(fields[4]);
        result.setCity(fields[5]);
        result.setState(fields[6]);
        result.setPostalCode(fields[7]);
        result.setStandardRate(new BigDecimal(fields[8]));
        result.setWeekendRate(new BigDecimal(fields[9]));
        return result;
    }
}
