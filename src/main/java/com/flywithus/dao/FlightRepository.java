package com.flywithus.dao;

import com.flywithus.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findFlights(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                             @Param("fromAirport") String fromAirport, @Param("toAirport") String toAirport);

    Optional<Flight> findByFromAirportNameAndToAirportNameAndFlightDate(String fromAirport, String toAirport, LocalDateTime flightDate);
}
