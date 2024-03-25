package com.pinheirolaio.bookingapi.bookings.application;

import com.pinheirolaio.bookingapi.bookings.model.Booking;
import com.pinheirolaio.bookingapi.hosts.application.HostsService;
import com.pinheirolaio.bookingapi.hosts.model.Block;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class BookingsServiceTest {

    BookingsService service;

    @Mock
    BookingsRepository repository;

    @Mock
    HostsService hostsService;

    @BeforeEach
    void setUp() {
        service = new BookingsService(repository, hostsService);
    }

    @Test
    public void hostHasNoBlockTest() {
        Booking booking = new Booking();
        booking.setCheckinDate(DateUtils.addDays(new Date(), 1));
        booking.setCheckoutDate(DateUtils.addDays(new Date(), 3));
        Block block = new Block();
        block.setStartDate(DateUtils.addDays(new Date(), 4));
        block.setEndDate(DateUtils.addDays(new Date(), 5));
        Assertions.assertTrue(service.reservationDateIsValid(booking, block));
    }

    @Test
    public void blockBeforeCheckoutTest() {
        Booking booking = new Booking();
        booking.setCheckinDate(DateUtils.addDays(new Date(), 4));
        booking.setCheckoutDate(DateUtils.addDays(new Date(), 6));
        Block block = new Block();
        block.setStartDate(DateUtils.addDays(new Date(), 1));
        block.setEndDate(DateUtils.addDays(new Date(), 3));
        Assertions.assertTrue(service.reservationDateIsValid(booking, block));
    }

    @Test
    public void blockStartBeforeCheckinBlockEndBeforeCheckoutTest() {
        Booking booking = new Booking();
        booking.setCheckinDate(DateUtils.addDays(new Date(), 3));
        booking.setCheckoutDate(DateUtils.addDays(new Date(), 5));
        Block block = new Block();
        block.setStartDate(DateUtils.addDays(new Date(), 1));
        block.setEndDate(DateUtils.addDays(new Date(), 7));
        Assertions.assertFalse(service.reservationDateIsValid(booking, block));
    }

    @Test
    public void blockStartAfterCheckinBlockEndBeforeCheckoutTest() {
        Booking booking = new Booking();
        booking.setCheckinDate(DateUtils.addDays(new Date(), 1));
        booking.setCheckoutDate(DateUtils.addDays(new Date(), 5));
        Block block = new Block();
        block.setStartDate(DateUtils.addDays(new Date(), 3));
        block.setEndDate(DateUtils.addDays(new Date(), 7));
        Assertions.assertFalse(service.reservationDateIsValid(booking, block));
    }


    @Test
    public void hostAlreadyHasAbookingTest() {
        Booking booking = new Booking();
        booking.setCheckinDate(DateUtils.addDays(new Date(), 2));
        booking.setCheckoutDate(DateUtils.addDays(new Date(), 3));
        Booking booking2 = new Booking();
        booking2.setCheckinDate(DateUtils.addDays(new Date(), 1));
        booking2.setCheckoutDate(DateUtils.addDays(new Date(), 5));
        Assertions.assertFalse(service.reservationDateIsValid(booking, booking2));
    }

    @Test
    public void hostDoesNotHaveAbookingTest() {
        Booking booking = new Booking();
        booking.setCheckinDate(DateUtils.addDays(new Date(), 2));
        booking.setCheckoutDate(DateUtils.addDays(new Date(), 3));
        Booking booking2 = new Booking();
        booking2.setCheckinDate(DateUtils.addDays(new Date(), 5));
        booking2.setCheckoutDate(DateUtils.addDays(new Date(), 8));
        Assertions.assertTrue(service.reservationDateIsValid(booking, booking2));
    }
}