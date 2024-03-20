package com.pinheirolaio.bookingapi.hosts.application;

import com.pinheirolaio.bookingapi.hosts.model.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostsRepository extends JpaRepository<Host, Long> {
}