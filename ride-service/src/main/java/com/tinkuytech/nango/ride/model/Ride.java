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
    private String driverUserId;

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    private String destination;

    @Column(name = "available_seats", nullable = false)
    private int availableSeats;

    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.OPEN;

    public enum Status {
        OPEN, COMPLETED, CANCELLED
    }

    public Ride() {}

    public Ride(String driverUserId, String origin, String destination, int availableSeats, LocalDateTime departureTime, Status status) {
        this.driverUserId = driverUserId;
        this.origin = origin;
        this.destination = destination;
        this.availableSeats = availableSeats;
        this.departureTime = departureTime;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDriverUserId() { return driverUserId; }
    public void setDriverUserId(String driverUserId) { this.driverUserId = driverUserId; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }

    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
