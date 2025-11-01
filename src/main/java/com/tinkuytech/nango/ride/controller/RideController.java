package com.tinkuytech.nango.ride.controller;

import com.tinkuytech.nango.ride.model.Ride;
import com.tinkuytech.nango.ride.dto.RideResponseDTO;
import com.tinkuytech.nango.ride.service.RideService;
import com.tinkuytech.nango.ride.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rides")
public class RideController {

    private final RideService rideService;
    private final JwtUtil jwtUtil;

    @Autowired
    public RideController(RideService rideService, JwtUtil jwtUtil) {
        this.rideService = rideService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RideResponseDTO createRide(@RequestBody Ride ride, HttpServletRequest request) {
        String token = extractToken(request);
        Long userId = jwtUtil.extractUserId(token);
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido o sin userId");
        }

        ride.setDriverUserId(userId);
        ride.setStatus(Ride.Status.OPEN);
        Ride createdRide = rideService.createRide(ride);
        return new RideResponseDTO(createdRide);
    }

    @GetMapping
    public List<RideResponseDTO> getAllRides() {
        return rideService.getAllRides().stream()
                .map(RideResponseDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RideResponseDTO getRideById(@PathVariable Long id) {
        Ride ride = rideService.getRideById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ride no encontrado"));
        return new RideResponseDTO(ride);
    }

    @GetMapping("/driver")
    public List<RideResponseDTO> getRidesByDriver(HttpServletRequest request) {
        String token = extractToken(request);
        Long userId = jwtUtil.extractUserId(token);
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido o sin userId");
        }

        return rideService.getRidesByDriverUserId(userId).stream()
                .map(RideResponseDTO::new)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}/join")
    public RideResponseDTO joinRide(@PathVariable Long id, HttpServletRequest request) {
        String token = extractToken(request);
        Long passengerId = jwtUtil.extractUserId(token);
        if (passengerId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido o sin userId");
        }

        Ride ride = rideService.getRideById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ride no encontrado"));

        if (ride.getAvailableSeats() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No hay asientos disponibles");
        }

        ride.setPassengerUserId(passengerId);
        ride.setAvailableSeats(ride.getAvailableSeats() - 1);

        Ride updatedRide = rideService.updateRide(ride);
        return new RideResponseDTO(updatedRide);
    }

    @PutMapping("/{id}")
    public RideResponseDTO updateRide(@PathVariable Long id, @RequestBody Ride rideDetails, HttpServletRequest request) {
        String token = extractToken(request);
        Long userId = jwtUtil.extractUserId(token);

        Ride ride = rideService.getRideById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ride no encontrado"));

        if (!ride.getDriverUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No puedes modificar este ride");
        }

        ride.setDestination(rideDetails.getDestination());
        ride.setOrigin(rideDetails.getOrigin());
        ride.setStatus(rideDetails.getStatus());
        ride.setAvailableSeats(rideDetails.getAvailableSeats());

        Ride updatedRide = rideService.updateRide(ride);
        return new RideResponseDTO(updatedRide);
    }

    @PutMapping("/{id}/rate")
    public RideResponseDTO rateRide(@PathVariable Long id, @RequestParam double rating, HttpServletRequest request) {
        String token = extractToken(request);
        Long passengerId = jwtUtil.extractUserId(token);

        Ride ride = rideService.getRideById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ride no encontrado"));

        if (ride.getPassengerUserId() == null || !ride.getPassengerUserId().equals(passengerId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Solo el pasajero puede calificar el viaje");
        }

        Ride ratedRide = rideService.rateRide(ride, rating, token);
        return new RideResponseDTO(ratedRide);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRide(@PathVariable Long id, HttpServletRequest request) {
        String token = extractToken(request);
        Long userId = jwtUtil.extractUserId(token);

        Ride ride = rideService.getRideById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ride no encontrado"));

        if (!ride.getDriverUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No puedes eliminar este ride");
        }

        rideService.deleteRide(id);
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Falta el token JWT");
    }
}
