package com.pinheirolaio.bookingapi.hosts.application;

import com.pinheirolaio.bookingapi.hosts.model.Block;
import com.pinheirolaio.bookingapi.hosts.model.Host;
import com.pinheirolaio.bookingapi.infrastructure.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlocksService {

    @Autowired
    BlocksRepository repository;

    @Autowired
    HostsService hostsService;

    public List<Block> findAll() {
        return repository.findAll();
    }

    public Block findById(Long id) throws NotFoundException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("block not found"));
    }

    public Block create(Block block, Host host) {
        block.setBlockId(null);
        block.setHost(host);
        return repository.save(block);
    }

    public void deleteOne(Long id) {
        repository.deleteById(id);
    }

    public Block update(Block current, Block block) {
        merge(current, block);
        repository.save(current);
        return current;
    }

    private void merge(Block current, Block block) {
        if (null != block) {
            if (null != block.getStartDate()) {
                current.setStartDate(block.getStartDate());
            }
            if (null != block.getEndDate()) {
                current.setEndDate(block.getEndDate());
            }
        }
    }

    public List<Block> findByHostId(Long hostId) {
        return repository.findByHostId(hostId);
    }
}
