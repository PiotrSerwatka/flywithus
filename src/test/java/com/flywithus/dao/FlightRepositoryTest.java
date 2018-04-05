package com.flywithus.dao;

import com.flywithus.BaseTest;
import com.flywithus.entity.Flight;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FlightRepositoryTest extends BaseTest {

    @Autowired
    private FlightRepository flightRepository;

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:database/flight/flightSearchScript.sql"),
            @Sql(scripts = "classpath:database/clean.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void shouldFindFlightsBetweenFirstAndTenthOfJanuary2018() {
        //given
        LocalDateTime from = LocalDateTime.of(2018,1,1,0,0);
        LocalDateTime to = LocalDateTime.of(2018,1,10,23,59);

        //when
        List<Flight> flights = flightRepository.findFlights(from,to,"Wroclaw", "Poznan");

        //then
        assertThat(flights).hasSize(3);
    }
}