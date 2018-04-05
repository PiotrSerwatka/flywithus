package com.flywithus.validation.validator.airport;

import com.flywithus.dao.AirportRepository;
import com.flywithus.dto.AirportTO;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class AirportExistValidator implements ConstraintValidator<AirportExist, AirportTO> {

    private AirportRepository airportRepository;

    public AirportExistValidator(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @Override
    public boolean isValid(AirportTO airport, ConstraintValidatorContext constraintValidatorContext) {
        return airport != null ? airportRepository.findByName(airport.getName()).isPresent() : false;
    }
}
