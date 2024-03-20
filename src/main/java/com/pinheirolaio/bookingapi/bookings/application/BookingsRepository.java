package com.pinheirolaio.bookingapi.bookings.application;


import com.pinheirolaio.bookingapi.bookings.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingsRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByHostId(Long hostId);
}
