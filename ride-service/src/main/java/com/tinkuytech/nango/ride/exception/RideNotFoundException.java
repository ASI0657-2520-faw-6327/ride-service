package com.tinkuytech.nango.ride.exception;

public class RideNotFoundException extends RuntimeException {
    public RideNotFoundException(Long id) {
        super("Ride con id " + id + " no encontrado");
    }
}
