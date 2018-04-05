package com.flywithus.service;


import com.flywithus.BaseTest;
import com.flywithus.dao.UserRepository;
import com.flywithus.data.TestUser;
import com.flywithus.dto.AddressTO;
import com.flywithus.dto.UserTO;
import com.flywithus.entity.User;
import com.flywithus.utility.ObjectConverter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class UserServiceTest extends BaseTest {

    @Autowired
    private ObjectConverter objectConverter;

    private UserRepository userRepository = Mockito.mock(UserRepository.class);

    private UserService userService;


    @Before
    public void beforeTest() {
        userService = new UserService(userRepository, objectConverter);
    }

    @Test
    public void shouldRegisterNewUser() {
        //given
        UserTO userTO = TestUser.createUser();
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        //when
        userService.registerUser(userTO);

        //then
        verify(userRepository, Mockito.times(1)).save(userCaptor.capture());
        User user = userCaptor.getValue();
        AddressTO address = userTO.getAddress();
        assertThat(user).extracting("firstName", "sureName", "address.city", "address.street", "address.houseNumber", "address.flatNumber")
                .contains(userTO.getFirstName(), userTO.getSureName(), address.getCity(), address.getStreet(), address.getHouseNumber(), address.getFlatNumber());
    }

}