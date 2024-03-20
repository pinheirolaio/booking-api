package com.pinheirolaio.bookingapi.guests.application;

import com.pinheirolaio.bookingapi.guests.model.Guest;
import com.pinheirolaio.bookingapi.infrastructure.exception.InvalidParameterException;
import com.pinheirolaio.bookingapi.infrastructure.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Guests", description = "manage guests in a booking")
@RestController
@RequestMapping
public class GuestsController {

    @Autowired
    GuestsService service;

    @Operation(summary = "get guests from a specific booking"
            , description = "get all the details of the guests of a specific booking.")
    @GetMapping("/bookings/{bookingId}/guests")
    public ResponseEntity findByBookingId(@PathVariable Long bookingId) {
        return new ResponseEntity<>(service.findByBookingId(bookingId), HttpStatus.OK);
    }

    @Operation(summary = "save guest in a specific booking"
            , description = "save guest in a specific booking. " +
            "It is necessary to carry out the operation one by one.")
    @PostMapping("/bookings/{bookingId}/guests")
    public ResponseEntity create(@PathVariable Long bookingId, @Valid @RequestBody Guest guest) throws InvalidParameterException, NotFoundException {
        return new ResponseEntity<>(service.create(guest, bookingId), HttpStatus.CREATED);
    }

    @Operation(summary = "delete a guest"
            , description = "It is not possible to delete a guest " +
            "if the booking is canceled or completed.")
    @DeleteMapping("/bookings/{bookingId}/guests/{id}")
    public ResponseEntity deleteOne(@PathVariable Long bookingId, @PathVariable Long id) throws InvalidParameterException, NotFoundException {
        service.findById(id);
        service.deleteOne(id, bookingId);
        return ResponseEntity.noContent().build();

    }

    @Operation(summary = "update guest information"
            , description = "update guest information")
    @PutMapping("/bookings/{bookingId}/guests/{id}")
    public ResponseEntity update(@PathVariable Long id, @Valid @RequestBody Guest guest) throws NotFoundException {
        return new ResponseEntity<>(service.update(service.findById(id), guest), HttpStatus.OK);
    }

}
