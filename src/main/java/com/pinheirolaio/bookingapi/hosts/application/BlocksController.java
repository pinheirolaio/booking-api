package com.pinheirolaio.bookingapi.hosts.application;

import com.pinheirolaio.bookingapi.hosts.model.Block;
import com.pinheirolaio.bookingapi.infrastructure.exception.InvalidParameterException;
import com.pinheirolaio.bookingapi.infrastructure.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Blocks", description = "manage blocks on a host's available schedule")
@RestController
public class BlocksController {

    @Autowired
    BlocksService service;

    @Autowired
    HostsService hostsService;

    @Operation(summary = "get blocks from a host"
            , description = "obtain blocks from a given host by providing its identifier.")
    @GetMapping("/hosts/{hostId}/blocks")
    public ResponseEntity findByHostId(@PathVariable Long hostId) {
        return new ResponseEntity<>(service.findByHostId(hostId), HttpStatus.OK);
    }

    @Operation(summary = "save block to a host"
            , description = "save block on a host, it must be properly created. " +
            "To create a host, use the operations in the 'Hosts' collection. " +
            "To save a block, the start and end date must be entered.")
    @PostMapping("/hosts/{hostId}/blocks")
    public ResponseEntity create(@PathVariable Long hostId, @Valid @RequestBody Block block) throws NotFoundException, InvalidParameterException {
        return new ResponseEntity<>(service.create(block, hostsService.findById(hostId)), HttpStatus.CREATED);
    }

    @Operation(summary = "remove block from a host"
            , description = "remove block from a host.")
    @DeleteMapping("/hosts/{hostId}/blocks/{id}")
    public ResponseEntity deleteOne(@PathVariable Long hostId, @PathVariable Long id) throws NotFoundException {
        service.findById(id);
        service.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "update a block on a host"
            , description = "The start and end dates of the block can be changed.")
    @PutMapping("/hosts/{hostId}/blocks/{id}")
    public ResponseEntity update(@PathVariable Long hostId
            , @PathVariable Long id, @Valid @RequestBody Block block) throws NotFoundException {
        return new ResponseEntity<>(service.update(service.findById(id), block), HttpStatus.OK);
    }

}
