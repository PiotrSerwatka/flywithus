package com.flywithus.data;

import com.flywithus.dto.FlightTO;
import com.flywithus.entity.Flight;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.util.Collections.emptyList;

public class TestFlight {

    public static FlightTO createFlight() {
        FlightTO flight = new FlightTO();
        flight.setFlightDate(LocalDateTime.of(2018, 1, 1, 10, 00));
        flight.setFromAirport(TestAirport.createFromAirport("Wroclaw"));
        flight.setToAirport(TestAirport.createFromAirport("Poznan"));
        flight.setNumberOfSeats(100);
        flight.setSeatPrice(BigDecimal.ONE);
        return flight;
    }

    public static Flight createFlightEntity() {
        Flight flight = new Flight();
        flight.setId(-1000l);
        flight.setFlightDate(LocalDateTime.of(2018, 1, 1, 10, 00));
        flight.setFromAirport(TestAirport.createFromAirportEntity("Wroclaw"));
        flight.setToAirport(TestAirport.createFromAirportEntity("Poznan"));
        flight.setNumberOfSeats(100);
        flight.setOrders(emptyList());
        flight.setSeatPrice(BigDecimal.ONE);
        return flight;
    }

}
