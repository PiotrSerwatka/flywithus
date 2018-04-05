package com.flywithus.utility.search;

import com.flywithus.dto.AirportTO;

import java.time.LocalDateTime;

public class FlightSearchData {

    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private AirportTO fromAirport;
    private AirportTO toAirport;
    private Integer numberOfSeats;

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getToDate() {
        return toDate;
    }

    public void setToDate(LocalDateTime toDate) {
        this.toDate = toDate;
    }

    public AirportTO getFromAirport() {
        return fromAirport;
    }

    public void setFromAirport(AirportTO fromAirport) {
        this.fromAirport = fromAirport;
    }

    public AirportTO getToAirport() {
        return toAirport;
    }

    public void setToAirport(AirportTO toAirport) {
        this.toAirport = toAirport;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
}
