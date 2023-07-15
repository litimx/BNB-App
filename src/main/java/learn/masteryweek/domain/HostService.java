package learn.masteryweek.domain;

import learn.masteryweek.data.HostRepository;
import learn.masteryweek.models.Host;
import java.util.List;

public class HostService {
    private final HostRepository repository;

    public HostService(HostRepository repository) {
        this.repository = repository;
    }

    public List<Host> findAll() {
        return repository.findAllHosts();
    }

    public Host findHostByEmail(String email) {
        return repository.findHostByEmail(email);
    }
}