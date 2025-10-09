package com.tinkuytech.nango.ride.repository;

import com.tinkuytech.nango.ride.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> findByDriverUserId(String driverUserId);
}
