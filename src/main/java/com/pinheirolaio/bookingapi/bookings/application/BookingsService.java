package com.pinheirolaio.bookingapi.bookings.application;

import com.pinheirolaio.bookingapi.bookings.model.Booking;
import com.pinheirolaio.bookingapi.bookings.model.BookingStatus;
import com.pinheirolaio.bookingapi.hosts.application.HostsService;
import com.pinheirolaio.bookingapi.hosts.model.Block;
import com.pinheirolaio.bookingapi.hosts.model.Host;
import com.pinheirolaio.bookingapi.infrastructure.exception.InvalidParameterException;
import com.pinheirolaio.bookingapi.infrastructure.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class BookingsService {

    BookingsRepository repository;

    HostsService hostsService;

    @Autowired
    public BookingsService(BookingsRepository repository, HostsService hostsService) {
        this.repository = repository;
        this.hostsService = hostsService;
    }

    public List<Booking> findAll() {
        return repository.findAll();
    }

    public Booking findById(Long id) throws NotFoundException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("booking not found"));
    }

    @Transactional
    public Booking create(Booking booking) throws InvalidParameterException, NotFoundException {
        booking.setHost(hostsService.findById(booking.getIdHost()));
        checkWhetherReservedOrBlocked(booking);
        booking.setBookingDate(new Date(System.currentTimeMillis() + TimeZone.getDefault().getRawOffset()));
        booking.setStatus(BookingStatus.PENDING);
        booking.setIdHost(null);
        booking.setId(null);
        return repository.save(booking);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Booking cancel(Booking booking) {
        booking.setStatus(BookingStatus.CANCELED);
        return repository.save(booking);
    }

    public Object rebook(Booking booking) throws InvalidParameterException, NotFoundException {
        booking.setStatus(BookingStatus.PENDING);
        checkWhetherReservedOrBlocked(booking);
        return repository.save(booking);
    }


    public Booking update(Booking currentBooking, Booking booking) {
        merge(currentBooking, booking);
        return repository.save(currentBooking);
    }

    private void merge(Booking current, Booking itemToUpdate) {
        if (null != itemToUpdate.getCheckinDate()) {
            current.setCheckinDate(itemToUpdate.getCheckinDate());
        }
        if (null != itemToUpdate.getCheckoutDate()) {
            current.setCheckoutDate(itemToUpdate.getCheckoutDate());
        }
        if (null != itemToUpdate.getStatus()) {
            current.setStatus(itemToUpdate.getStatus());
        }
    }


    private void checkWhetherReservedOrBlocked(Booking booking) throws InvalidParameterException, NotFoundException {
        Host host = hostsService.findById(booking.getHost().getId());

        if (!host.getActive()) {
            throw new InvalidParameterException("the host is no longer active");
        }

        if (null != booking.getHost().getId()) {
            if (null != host.getBlocks() && host.getBlocks().size() > 0) {
                for (Block block : host.getBlocks()) {
                    if (!reservationDateIsValid(booking, block)) {
                        throw new InvalidParameterException("the host has a block for the given date");
                    }
                }
            }

            List<Booking> bookingList = repository.findByHostId(booking.getHost().getId());
            if (null != bookingList && bookingList.size() > 0) {
                System.out.println("there are other bookings for this host");
                for (Booking previousBooking : bookingList) {
                    if (!reservationDateIsValid(booking, previousBooking)) {
                        throw new InvalidParameterException("the date provided is already booked");
                    }
                }
            }
        }
    }

    public boolean reservationDateIsValid(Booking booking, Block block) {
        if (booking.getCheckinDate().before(new Date())) {
            return false;
        }
        if (booking.getCheckoutDate().before(booking.getCheckinDate())) {
            return false;
        }
        if (booking.getCheckinDate().before(block.getStartDate())) {
            return booking.getCheckoutDate().before(block.getStartDate());
        } else if (booking.getCheckinDate().after(block.getStartDate())) {
            return booking.getCheckinDate().after(block.getEndDate());
        } else {
            return false;
        }
    }

    public boolean reservationDateIsValid(Booking currentBooking, Booking previousBooking) {
        if (currentBooking.getCheckinDate().before(previousBooking.getCheckinDate())) {
            return currentBooking.getCheckoutDate().before(previousBooking.getCheckinDate());
        } else if (currentBooking.getCheckinDate().after(previousBooking.getCheckinDate())) {
            return currentBooking.getCheckinDate().after(previousBooking.getCheckoutDate());
        } else {
            return false;
        }
    }
}
