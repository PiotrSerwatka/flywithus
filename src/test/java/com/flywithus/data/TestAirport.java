package com.flywithus.data;

import com.flywithus.dto.AirportTO;
import com.flywithus.entity.Airport;

public class TestAirport {

    public static AirportTO createFromAirport(String name) {
        AirportTO airport = new AirportTO();
        airport.setName(name);
        return airport;
    }

    public static Airport createFromAirportEntity(String name) {
        Airport airport = new Airport();
        airport.setName(name);
        return airport;
    }
}
