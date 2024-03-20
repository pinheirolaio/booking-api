package com.pinheirolaio.bookingapi.hosts.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name="host")
public class Host {

    @Schema(hidden = true)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Schema(description = "host name", example = "Amazing Host", required = true)
    @NotNull(message = "name cannot be null")
    @Column(name = "name")
    private String name;

    @Schema(description = "host email", example = "amazing.host@mail.com", required = true)
    @NotNull(message = "email cannot be null")
    @Column(name = "email")
    private String email;

    @Schema(description = "host address", example = "300 Hidden Figures Way SW, Washington, D.C", required = true)
    @NotNull(message = "address cannot be null")
    @Column(name = "address")
    private String address;

    @Schema(description = "host active. (true or false)", example = "true")
    @Column(name = "active")
    private Boolean active;

    @Schema(hidden = true)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH, mappedBy = "host")
    private List<Block> blocks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Host{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", active=" + active +
                ", blocks=" + blocks +
                '}';
    }
}
