package com.flywithus.service;

import com.flywithus.dao.UserRepository;
import com.flywithus.dto.UserTO;
import com.flywithus.entity.User;
import com.flywithus.utility.ObjectConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;
    private ObjectConverter objectConverter;

    public UserService(UserRepository userRepository, ObjectConverter objectConverter) {
        this.userRepository = userRepository;
        this.objectConverter = objectConverter;
    }

    public void registerUser(UserTO userTO) {
        User user = objectConverter.convert(userTO, User.class);
        userRepository.save(user);
    }
}
