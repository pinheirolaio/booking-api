package com.pinheirolaio.bookingapi.bookings.application;


import com.pinheirolaio.bookingapi.bookings.model.Booking;
import com.pinheirolaio.bookingapi.infrastructure.exception.InvalidParameterException;
import com.pinheirolaio.bookingapi.infrastructure.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Bookings", description = "manage bookings")
@RestController
@RequestMapping("/bookings")
@Validated
public class BookingsController {

    @Autowired
    BookingsService service;

    @Operation(summary = "find all bookings"
            , description = "Get details of all bookings, including guests, hosts and their blocks")
    @GetMapping
    public ResponseEntity findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "find a booking by id"
            , description = "Get details of a booking, including guests, hosts and their blocks")
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "creates a new booking"
            , description = "Creates a new booking as long as the host does not have " +
            "a block for the requested dates and the host is not reserved for someone. " +
            "You must provide a host identifier, so you must register a host before making a reservation. " +
            "To create the reservation, only the host identifier, check-in and checkout date must be entered. " +
            "To add guests, use the guest collection operations providing the booking identifier.")
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Booking booking) throws InvalidParameterException, NotFoundException {
        System.out.println("creating the booking: " + booking);
        return new ResponseEntity<>(service.create(booking), HttpStatus.CREATED);
    }

    @Operation(summary = "delete a booking from the system"
            , description = "delete a booking from the system, " +
            "be careful as this operation does not represent a logical deletion " +
            "and will also result in the exclusion of guests")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteOne(@PathVariable Long id) throws NotFoundException {
        service.findById(id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "cancel a booking"
            , description = "When canceling a reservation it will remain " +
            "saved in history and can be redone using the 'rebook' operation")
    @DeleteMapping("/{id}/cancel")
    public ResponseEntity cancel(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(service.cancel(service.findById(id)), HttpStatus.OK);
    }

    @Operation(summary = "update booking details"
            , description = "It is only possible to update the status and check-in and check-out dates. " +
            "To update the guests use the operations from the 'Guests' collection " +
            "and to update the host use the operations from the 'Hosts' collection")
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Booking booking) throws NotFoundException {
        return new ResponseEntity<>(service.update(service.findById(id), booking), HttpStatus.OK);
    }

    @Operation(summary = "Rebook a canceled booking"
            , description = "It is possible to rebook a canceled booking as long " +
            "as the host does not have a block for the requested dates and the host is not reserved for someone")
    @GetMapping("/{id}/rebook")
    public ResponseEntity rebook(@PathVariable Long id) throws InvalidParameterException, NotFoundException {
        return new ResponseEntity<>(service.rebook(service.findById(id)), HttpStatus.OK);
    }

}
