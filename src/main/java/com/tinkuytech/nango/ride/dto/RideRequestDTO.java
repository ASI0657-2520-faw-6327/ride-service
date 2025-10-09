package com.tinkuytech.nango.ride.dto;

import java.time.LocalDateTime;

public class RideRequestDTO {

    private String origin;
    private String destination;
    private int availableSeats;
    private LocalDateTime departureTime;

    public RideRequestDTO() {}

    public RideRequestDTO(String origin, String destination, int availableSeats, LocalDateTime departureTime) {
        this.origin = origin;
        this.destination = destination;
        this.availableSeats = availableSeats;
        this.departureTime = departureTime;
    }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }

    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }
}
