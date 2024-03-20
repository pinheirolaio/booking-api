package com.pinheirolaio.bookingapi.guests.application;

import com.pinheirolaio.bookingapi.bookings.application.BookingsService;
import com.pinheirolaio.bookingapi.bookings.model.Booking;
import com.pinheirolaio.bookingapi.bookings.model.BookingStatus;
import com.pinheirolaio.bookingapi.guests.model.Guest;
import com.pinheirolaio.bookingapi.infrastructure.exception.InvalidParameterException;
import com.pinheirolaio.bookingapi.infrastructure.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestsService {

    @Autowired
    GuestsRepository repository;

    @Autowired
    BookingsService bookingsService;

    public List<Guest> findAll() {
        return repository.findAll();
    }

    public Guest findById(Long id) throws NotFoundException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("guest not found"));
    }

    public Guest create(Guest guest, Long bookingId) throws InvalidParameterException, NotFoundException {
        Booking booking = bookingsService.findById(bookingId);
        checkIfBookingStatusAllowChanges(booking);
        guest.setBooking(booking);
        guest.setId(null);
        return repository.save(guest);

    }

    public void deleteOne(Long id, Long bookingId) throws InvalidParameterException, NotFoundException {
        checkIfBookingStatusAllowChanges(bookingsService.findById(bookingId));
        repository.deleteById(id);
    }

    private void checkIfBookingStatusAllowChanges(Booking booking) throws InvalidParameterException, NotFoundException {
        if (!(booking.getStatus() == BookingStatus.PENDING
                || booking.getStatus() == BookingStatus.CONFIRMED)) {
            throw new InvalidParameterException("booking status does not allow changing guests");
        }

    }

    public Guest update(Guest currentGuest, Guest guest) {
        merge(currentGuest, guest);
        repository.save(currentGuest);
        return currentGuest;
    }

    private void merge(Guest current, Guest guest) {
        if (null != guest.getName()){
            current.setName(guest.getName());
        }
        if (null != guest.getEmail()){
            current.setEmail(guest.getEmail());
        }
    }

    public List<Guest> findByBookingId(Long bookingId) {
        return repository.findByBookingId(bookingId);
    }
}
