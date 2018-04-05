package com.flywithus.converter;

import com.flywithus.dao.AirportRepository;
import com.flywithus.dto.AirportTO;
import com.flywithus.entity.Airport;
import org.dozer.CustomConverter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AirportConverter implements CustomConverter {

    private AirportRepository flightDao;

    public AirportConverter(AirportRepository flightDao) {
        this.flightDao = flightDao;
    }

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        AirportTO from = (AirportTO) sourceFieldValue;
        Optional<Airport> optionalAirport = flightDao.findByName(from.getName());
        return optionalAirport.isPresent() ? optionalAirport.get() : existingDestinationFieldValue;
    }
}
