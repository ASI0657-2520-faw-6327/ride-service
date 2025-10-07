package com.tinkuytech.nango.ride.service;

import com.tinkuytech.nango.ride.exception.RideNotFoundException;
import com.tinkuytech.nango.ride.model.Ride;
import com.tinkuytech.nango.ride.repository.RideRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final RestTemplate restTemplate;

    @Value("${iam.service.url}")
    private String iamServiceUrl;

    public RideServiceImpl(RideRepository rideRepository, RestTemplate restTemplate) {
        this.rideRepository = rideRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public Ride createRide(Ride ride) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                throw new RuntimeException("⚠️ No se encontró autenticación válida en el contexto de seguridad");
            }

            String token = authentication.getCredentials() != null
                    ? authentication.getCredentials().toString()
                    : null;

            if (token == null || token.isEmpty()) {
                throw new RuntimeException("⚠️ No se encontró el token JWT en el contexto de seguridad");
            }

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            String url = iamServiceUrl + "/api/users/exists/" + ride.getDriverUserId();
            ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.GET, entity, Boolean.class);

            Boolean userExists = response.getBody();

            if (userExists == null || !userExists) {
                throw new RuntimeException("❌ El usuario con ID " + ride.getDriverUserId() + " no existe en IAM");
            }

            return rideRepository.save(ride);

        } catch (HttpClientErrorException e) {
            throw new RuntimeException("❌ Error al comunicarse con el servicio IAM: "
                    + e.getStatusCode() + " - " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("⚠️ Error inesperado al crear el ride: " + e.getMessage());
        }
    }

    @Override
    public List<Ride> getAllRides() {
        return rideRepository.findAll();
    }

    @Override
    public Optional<Ride> getRideById(Long id) {
        return rideRepository.findById(id);
    }

    @Override
    public List<Ride> getRidesByDriverUserId(String driverUserId) {
        return rideRepository.findByDriverUserId(driverUserId);
    }

    @Override
    public Ride updateRide(Ride ride) {
        if (!rideRepository.existsById(ride.getId())) {
            throw new RideNotFoundException(ride.getId());
        }
        return rideRepository.save(ride);
    }

    @Override
    public void deleteRide(Long id) {
        if (!rideRepository.existsById(id)) {
            throw new RideNotFoundException(id);
        }
        rideRepository.deleteById(id);
    }
}
