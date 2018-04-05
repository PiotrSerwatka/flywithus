package com.flywithus.converter;

import com.flywithus.dao.UserRepository;
import com.flywithus.dto.UserTO;
import org.dozer.CustomConverter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserConverter implements CustomConverter {

    private UserRepository userRepository;

    public UserConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        Optional<UserTO> fromOptional = Optional.ofNullable((UserTO) sourceFieldValue);
        return fromOptional.isPresent() ? userRepository.findByUserId(fromOptional.get().getUserId()) : existingDestinationFieldValue;
    }
}
