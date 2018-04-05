package com.flywithus.service;

import com.flywithus.dao.FlightRepository;
import com.flywithus.dto.FlightTO;
import com.flywithus.entity.Flight;
import com.flywithus.type.OrderStatus;
import com.flywithus.utility.ObjectConverter;
import com.flywithus.utility.search.FlightSearchData;
import com.flywithus.validation.ValidationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class FlightService {

    private FlightRepository flightRepository;
    private ObjectConverter objectConverter;
    private ValidationService validationService;

    public FlightService(FlightRepository flightRepository, ObjectConverter objectConverter, ValidationService validationService) {
        this.flightRepository = flightRepository;
        this.objectConverter = objectConverter;
        this.validationService = validationService;
    }

    public void createFlight(FlightTO flightTO) {
        validationService.validate(flightTO);

        Flight flight = objectConverter.convert(flightTO, Flight.class);
        flightRepository.save(flight);
    }

    public List<FlightTO> findFlights(FlightSearchData flightSearchData) {
        List<Flight> flights = flightRepository.findFlights(flightSearchData.getFromDate(), flightSearchData.getToDate(),
                flightSearchData.getFromAirport().getName(), flightSearchData.getToAirport().getName());
        List<Flight> flightsWithFreeSeats = flights.stream()
                .filter(flight -> flight.getOrders().stream()
                        .filter(order -> order.getOrderStatus() == OrderStatus.PAID)
                        .mapToInt(order -> order.getNumberOfTickets())
                        .sum() + flightSearchData.getNumberOfSeats() <= flight.getNumberOfSeats())
                .collect(toList());
        return objectConverter.convert(flightsWithFreeSeats, FlightTO.class);
    }
}
