package com.flywithus.data;

import com.flywithus.dto.AddressTO;
import com.flywithus.dto.UserTO;

import java.util.UUID;

public class TestUser {

    public static UserTO createUser() {
        UserTO user = new UserTO();
        user.setUserId(UUID.randomUUID().toString());
        user.setFirstName("FirstName");
        user.setSureName("SureName");
        user.setAddress(createAddress());
        return user;
    }

    public static AddressTO createAddress() {
        AddressTO address = new AddressTO();
        address.setCity("City");
        address.setStreet("Street");
        address.setHouseNumber("1");
        address.setFlatNumber("1");
        return address;
    }
}
