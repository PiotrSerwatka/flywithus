package com.flywithus.service;

import com.flywithus.BaseTest;
import com.flywithus.dao.FlightRepository;
import com.flywithus.data.TestAirport;
import com.flywithus.data.TestFlight;
import com.flywithus.data.TestOrder;
import com.flywithus.dto.FlightTO;
import com.flywithus.entity.Flight;
import com.flywithus.type.OrderStatus;
import com.flywithus.utility.ObjectConverter;
import com.flywithus.utility.search.FlightSearchData;
import com.flywithus.validation.ValidationService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.Arrays;
import java.util.List;

import static com.flywithus.data.TestFlight.createFlightEntity;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

public class FlightServiceTest extends BaseTest {

    @Autowired
    private ObjectConverter objectConverter;
    @Autowired
    private ValidationService validationService;

    private FlightRepository flightRepository = Mockito.mock(FlightRepository.class);

    private FlightService flightService;


    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    @Before
    public void beforeTest() {
        flightService = new FlightService(flightRepository, objectConverter, validationService);
    }

    @Test
    @SqlGroup({
            @Sql(statements = "insert into airport values (-1000, 'Wroclaw'), (-1001, 'Poznan')"),
            @Sql(scripts = "classpath:database/clean.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void shouldCreateNewFlight() {
        //given
        FlightTO flightTO = TestFlight.createFlight();
        ArgumentCaptor<Flight> flightCaptor = ArgumentCaptor.forClass(Flight.class);

        //when
        flightService.createFlight(flightTO);

        //then
        verify(flightRepository, Mockito.times(1)).save(flightCaptor.capture());
        Flight flight = flightCaptor.getValue();
        assertThat(flight).extracting("fromAirport.name", "toAirport.name", "flightDate", "numberOfSeats")
                .contains(flightTO.getFromAirport().getName(), flightTO.getToAirport().getName(), flightTO.getFlightDate(), flightTO.getNumberOfSeats());
    }


    @Test
    public void shouldThrowValidationExceptionsIfWrongFieldValues() {
        //given
        FlightTO flightTO = new FlightTO();
        expectedException.expect(javax.validation.ValidationException.class);
        expectedException.expectMessage(allOf(
                containsString("toAirport airport not found"),
                containsString("fromAirport airport not found"),
                containsString("numberOfSeats must not be null"),
                containsString("flightDate must not be null")
        ));

        //when
        flightService.createFlight(flightTO);

        //then
    }

    @Test
    public void shouldFindFlightsAndMapResultsToTransferObjects() {
        //given
        FlightSearchData searchData = new FlightSearchData();
        searchData.setFromAirport(TestAirport.createFromAirport("Wroclaw"));
        searchData.setToAirport(TestAirport.createFromAirport("Poznan"));
        searchData.setNumberOfSeats(100);
        Flight flightWithFreeSeats = createFlightEntity();
        Flight flightWithNoSeatsFree = createFlightEntity();
        flightWithNoSeatsFree.setOrders(singletonList(TestOrder.createOrderEntity(50, OrderStatus.PAID)));
        given(flightRepository.findFlights(any(), any(), any(), any())).willReturn(Arrays.asList(flightWithFreeSeats, flightWithNoSeatsFree));

        //when
        List<FlightTO> flights = flightService.findFlights(searchData);

        //then
        assertThat(flights).hasSize(1);
        assertThat(flights.iterator().next()).extracting("fromAirport.name", "toAirport.name", "flightDate", "numberOfSeats")
                .contains(flightWithFreeSeats.getFromAirport().getName(), flightWithFreeSeats.getToAirport().getName(), flightWithFreeSeats.getFlightDate(), flightWithFreeSeats.getNumberOfSeats());
    }

}