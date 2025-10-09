package com.tinkuytech.nango.ride.service;

import com.tinkuytech.nango.ride.model.Ride;

import java.util.List;
import java.util.Optional;

public interface RideService {
    Ride createRide(Ride ride);
    List<Ride> getAllRides();
    Optional<Ride> getRideById(Long id);
    List<Ride> getRidesByDriverUserId(String driverUserId);
    Ride updateRide(Ride ride);
    void deleteRide(Long id);
}
