package com.flywithus.converter;

import com.flywithus.dao.FlightRepository;
import com.flywithus.dto.FlightTO;
import com.flywithus.entity.Flight;
import org.dozer.CustomConverter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FlightConverter implements CustomConverter {

    private FlightRepository flightRepository;

    public FlightConverter(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        FlightTO from = (FlightTO) sourceFieldValue;
        Optional<Flight> optionalFlight = flightRepository.findByFromAirportNameAndToAirportNameAndFlightDate(
                from.getFromAirport().getName(), from.getToAirport().getName(), from.getFlightDate());
        return optionalFlight.orElseThrow(() -> new RuntimeException(String.format("Flight not found %s", from)));
    }
}
