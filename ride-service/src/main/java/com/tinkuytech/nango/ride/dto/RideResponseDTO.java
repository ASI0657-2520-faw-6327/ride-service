package com.tinkuytech.nango.ride.dto;

import com.tinkuytech.nango.ride.model.Ride;
import java.time.LocalDateTime;

public class RideResponseDTO {

    private Long id;
    private String driverUserId;
    private String origin;
    private String destination;
    private int availableSeats;
    private LocalDateTime departureTime;
    private Ride.Status status;

    public RideResponseDTO() {}

    public RideResponseDTO(Ride ride) {
        this.id = ride.getId();
        this.driverUserId = ride.getDriverUserId();
        this.origin = ride.getOrigin();
        this.destination = ride.getDestination();
        this.availableSeats = ride.getAvailableSeats();
        this.departureTime = ride.getDepartureTime();
        this.status = ride.getStatus();
    }

    public Long getId() { return id; }
    public String getDriverUserId() { return driverUserId; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public int getAvailableSeats() { return availableSeats; }
    public LocalDateTime getDepartureTime() { return departureTime; }
    public Ride.Status getStatus() { return status; }
}
