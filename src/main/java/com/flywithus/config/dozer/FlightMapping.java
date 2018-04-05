package com.flywithus.config.dozer;

import com.flywithus.converter.AirportConverter;
import com.flywithus.dto.FlightTO;
import com.flywithus.entity.Flight;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlightMapping {

    @Bean
    public BeanMappingBuilder flightToToFlightEntityMappingBuilder() {
        return new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(FlightTO.class, Flight.class, TypeMappingOptions.oneWay())
                        .fields("flightDate", "flightDate", fmb -> fmb.copyByReference(true))
                        .fields("fromAirport", "fromAirport", fmb -> fmb.customConverter(AirportConverter.class))
                        .fields("toAirport", "toAirport", fmb -> fmb.customConverter(AirportConverter.class));
            }
        };
    }

    @Bean
    public BeanMappingBuilder flightEntityToFlightToMappingBuilder() {
        return new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(Flight.class, FlightTO.class, TypeMappingOptions.oneWay())
                        .fields("flightDate", "flightDate", fmb -> fmb.copyByReference(true))
                        .fields("fromAirport", "fromAirport")
                        .fields("toAirport", "toAirport");
            }
        };
    }
}
