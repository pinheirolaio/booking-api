package com.pinheirolaio.bookingapi.hosts.application;

import com.pinheirolaio.bookingapi.hosts.model.Host;
import com.pinheirolaio.bookingapi.infrastructure.exception.InvalidParameterException;
import com.pinheirolaio.bookingapi.infrastructure.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Hosts", description = "manage hosts")
@RestController
@RequestMapping("/hosts")
public class HostsController {

    @Autowired
    HostsService service;

    @Operation(summary = "find all hosts"
            , description = "obtains all registered hosts, including disabled ones")
    @GetMapping
    public ResponseEntity findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "get a host by identifier", description = "get a host by identifier")
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) throws NotFoundException, InvalidParameterException {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "create a new host", description = "creates a new active host")
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Host host) {
        return new ResponseEntity<>(service.create(host), HttpStatus.CREATED);
    }

    @Operation(summary = "disable a host"
            , description = "a host will never be deleted from the system," +
            " it remains disabled for booking history")
    @DeleteMapping("/{id}")
    public ResponseEntity disable(@PathVariable Long id) throws NotFoundException, InvalidParameterException {
        return new ResponseEntity<>(service.disable(service.findById(id)), HttpStatus.OK);
    }

    @Operation(summary = "update a host"
            , description = "the name, email, address and the flag that " +
            "indicates whether it is active or disabled can be changed")
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @Valid @RequestBody Host host) throws NotFoundException, InvalidParameterException {
        return new ResponseEntity<>(service.update(service.findById(id), host), HttpStatus.OK);
    }

}
