package com.pinheirolaio.bookingapi.bookings.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pinheirolaio.bookingapi.guests.model.Guest;
import com.pinheirolaio.bookingapi.hosts.model.Host;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name="booking")
public class Booking {

    @Schema(hidden = true)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Schema(hidden = true)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH, mappedBy = "booking")
    private List<Guest> guests;

    @Schema(hidden = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_host")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Host host;

    @Schema(hidden = true)
    @Column(name = "booking_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date bookingDate;

    @Schema(description = "check-in date. Example: 2024-04-23 15:30:00"
            , example = "2024-04-23 15:30:00", required = true)
    @NotNull(message = "check-in date cannot be null")
    @Column(name = "checkin_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date checkinDate;

    @Schema(description = "check-out date Example: 2024-04-28 15:30:00"
            , example = "2024-04-28 15:30:00", required = true)
    @NotNull(message = "check-out date cannot be null")
    @Column(name = "checkout_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date checkoutDate;

    @Schema(description = "booking status PENDING, CONFIRMED, CANCELED or COMPLETED. " +
            "In the creation operation the status does not need to be informed, " +
            "it will always be pending."
            , example = "CONFIRMED")
    @Column(name = "status")
    private BookingStatus status;

    @Schema(description = "Host identifier. " +
            "It is only necessary when creating, when updating it will not be used", example = "1")
    @Transient
    private Long idHost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Guest> getGuests() {
        return guests;
    }

    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Date getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(Date checkinDate) {
        this.checkinDate = checkinDate;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public Long getIdHost() {
        return idHost;
    }

    public void setIdHost(Long idHost) {
        this.idHost = idHost;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", guests=" + guests +
                ", host=" + host +
                ", bookingDate=" + bookingDate +
                ", checkinDate=" + checkinDate +
                ", checkoutDate=" + checkoutDate +
                ", status=" + status +
                ", idHost=" + idHost +
                '}';
    }
}
