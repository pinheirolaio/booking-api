package com.pinheirolaio.bookingapi.bookings.model;

/**
 * PENDING: The booking has been requested but not yet confirmed or approved.
 * CONFIRMED: The booking has been confirmed by the host or the system.
 * CANCELED: The booking has been canceled by either the guest or the host.
 * COMPLETED: The booking has been successfully completed, and the guest has checked out.
 */
public enum BookingStatus {
    PENDING,
    CONFIRMED,
    CANCELED,
    COMPLETED;
}
