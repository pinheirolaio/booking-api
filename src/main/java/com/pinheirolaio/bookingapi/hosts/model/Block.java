package com.pinheirolaio.bookingapi.hosts.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name="block")
public class Block {
    @Schema(hidden = true)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long blockId;

    @Schema(description = "block start date Example: 2024-04-22 15:30:00"
            , example = "2024-04-22 15:30:00", required = true)
    @NotNull(message = "block start date cannot be null")
    @Column(name = "start_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    @Schema(description = "block end date. Example: 2024-04-25 12:30:00"
            , example = "2024-04-25 12:30:00", required = true)
    @NotNull(message = "block end date cannot be null")
    @Column(name = "end_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    @Schema(hidden = true)
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_host")
    private Host host;

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return "Block{" +
                "blockId=" + blockId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
