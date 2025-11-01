package com.tinkuytech.nango.ride.dto;

import com.tinkuytech.nango.ride.model.Ride;
import java.time.LocalDateTime;

public class RideResponseDTO {

    private Long id;
    private Long driverUserId;
    private Long passengerUserId;
    private String origin;
    private String destination;
    private int availableSeats;
    private LocalDateTime departureTime;
    private Ride.Status status;
    private Double rating;             
    private Double driverAverageRating; 

    public RideResponseDTO() {}

    public RideResponseDTO(Ride ride) {
        this.id = ride.getId();
        this.driverUserId = ride.getDriverUserId();
        this.passengerUserId = ride.getPassengerUserId();
        this.origin = ride.getOrigin();
        this.destination = ride.getDestination();
        this.availableSeats = ride.getAvailableSeats();
        this.departureTime = ride.getDepartureTime();
        this.status = ride.getStatus();
        this.rating = ride.getRating();
        this.driverAverageRating = ride.getDriverAverageRating();
    }

    public Long getId() { return id; }
    public Long getDriverUserId() { return driverUserId; }
    public Long getPassengerUserId() { return passengerUserId; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public int getAvailableSeats() { return availableSeats; }
    public LocalDateTime getDepartureTime() { return departureTime; }
    public Ride.Status getStatus() { return status; }
    public Double getRating() { return rating; }
    public Double getDriverAverageRating() { return driverAverageRating; }

    public void setId(Long id) { this.id = id; }
    public void setDriverUserId(Long driverUserId) { this.driverUserId = driverUserId; }
    public void setPassengerUserId(Long passengerUserId) { this.passengerUserId = passengerUserId; }
    public void setOrigin(String origin) { this.origin = origin; }
    public void setDestination(String destination) { this.destination = destination; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }
    public void setStatus(Ride.Status status) { this.status = status; }
    public void setRating(Double rating) { this.rating = rating; }
    public void setDriverAverageRating(Double driverAverageRating) { this.driverAverageRating = driverAverageRating; }
}
