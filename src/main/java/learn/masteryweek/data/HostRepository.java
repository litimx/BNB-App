package learn.masteryweek.data;
import learn.masteryweek.models.Guest;
import learn.masteryweek.models.Host;
import java.util.List;

public interface HostRepository {
    Host findHostByEmail(String hostEmail);

    Host findHostByFile(String fileName);

    List<Host> findAllHosts();
}