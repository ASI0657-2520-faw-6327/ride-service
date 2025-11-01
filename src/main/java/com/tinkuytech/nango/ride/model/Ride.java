package com.tinkuytech.nango.ride.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rides")
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "driver_user_id", nullable = false)
    private Long driverUserId;

    @Column(name = "passenger_user_id")
    private Long passengerUserId;

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    private String destination;

    @Column(name = "available_seats", nullable = false)
    private int availableSeats;

    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "driver_average_rating")
    private Double driverAverageRating;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.OPEN;

    public enum Status {
        OPEN, COMPLETED, CANCELLED
    }

    public Ride() {}

    public Ride(Long driverUserId, String origin, String destination,
                int availableSeats, LocalDateTime departureTime, Status status) {
        this.driverUserId = driverUserId;
        this.origin = origin;
        this.destination = destination;
        this.availableSeats = availableSeats;
        this.departureTime = departureTime;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDriverUserId() {
        return driverUserId;
    }

    public void setDriverUserId(Long driverUserId) {
        this.driverUserId = driverUserId;
    }

    public Long getPassengerUserId() {
        return passengerUserId;
    }

    public void setPassengerUserId(Long passengerUserId) {
        this.passengerUserId = passengerUserId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getDriverAverageRating() {
        return driverAverageRating;
    }

    public void setDriverAverageRating(Double driverAverageRating) {
        this.driverAverageRating = driverAverageRating;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
