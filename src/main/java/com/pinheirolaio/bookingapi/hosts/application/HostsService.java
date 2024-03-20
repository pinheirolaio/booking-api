package com.pinheirolaio.bookingapi.hosts.application;

import com.pinheirolaio.bookingapi.hosts.model.Host;
import com.pinheirolaio.bookingapi.infrastructure.exception.InvalidParameterException;
import com.pinheirolaio.bookingapi.infrastructure.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostsService {

    @Autowired
    HostsRepository repository;

    public List<Host> findAll() {
        return repository.findAll();
    }

    public Host findById(Long id) throws NotFoundException, InvalidParameterException {
        if (null != id) {
            return repository.findById(id).orElseThrow(() -> new NotFoundException("the given host was not found"));
        } else {
            throw new InvalidParameterException("host identifier needs to be filled in");
        }
    }

    public Host create(Host host) {
        host.setId(null);
        host.setActive(true);
        return repository.save(host);
    }

    public Host disable(Host host) {
        host.setActive(false);
        return repository.save(host);
    }

    public Host update(Host currentHost, Host host) {
        merge(currentHost, host);
        repository.save(currentHost);
        return currentHost;
    }

    private void merge(Host current, Host host) {
        if (null != host.getName()){
            current.setName(host.getName());
        }
        if (null != host.getEmail()){
            current.setEmail(host.getEmail());
        }
        if (null != host.getActive()){
            current.setActive(host.getActive());
        }
    }

}
