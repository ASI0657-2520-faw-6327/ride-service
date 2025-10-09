package com.tinkuytech.nango.ride.controller;

import com.tinkuytech.nango.ride.model.Ride;
import com.tinkuytech.nango.ride.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/rides")
public class RideController {

    private final RideService rideService;

    @Autowired
    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ride createRide(@RequestBody Ride ride) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ride.setDriverUserId(auth.getName());
        ride.setStatus(Ride.Status.OPEN);
        return rideService.createRide(ride);
    }

    @GetMapping
    public List<Ride> getAllRides() {
        return rideService.getAllRides();
    }

    @GetMapping("/{id}")
    public Ride getRideById(@PathVariable Long id) {
        return rideService.getRideById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ride no encontrado"));
    }

    @GetMapping("/driver")
    public List<Ride> getRidesByDriver() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String driverUserId = auth.getName();
        return rideService.getRidesByDriverUserId(driverUserId);
    }

    @PutMapping("/{id}")
    public Ride updateRide(@PathVariable Long id, @RequestBody Ride rideDetails) {
        Ride ride = rideService.getRideById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ride no encontrado"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!ride.getDriverUserId().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No puedes modificar este ride");
        }

        ride.setDestination(rideDetails.getDestination());
        ride.setOrigin(rideDetails.getOrigin());
        ride.setStatus(rideDetails.getStatus());
        ride.setAvailableSeats(rideDetails.getAvailableSeats());

        return rideService.updateRide(ride);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRide(@PathVariable Long id) {
        Ride ride = rideService.getRideById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ride no encontrado"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!ride.getDriverUserId().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No puedes eliminar este ride");
        }

        rideService.deleteRide(id);
    }
}
