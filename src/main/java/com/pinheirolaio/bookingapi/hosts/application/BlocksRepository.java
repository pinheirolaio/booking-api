package com.pinheirolaio.bookingapi.hosts.application;

import com.pinheirolaio.bookingapi.hosts.model.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlocksRepository extends JpaRepository<Block, Long> {
    List<Block> findByHostId(Long hostId);
}