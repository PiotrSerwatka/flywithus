package com.flywithus.dto;

import com.flywithus.validation.validator.airport.AirportExist;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FlightTO {

    @AirportExist
    private AirportTO fromAirport;
    @AirportExist
    private AirportTO toAirport;
    @NotNull
    private LocalDateTime flightDate;
    @NotNull
    private Integer numberOfSeats;
    private BigDecimal seatPrice;

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

    public LocalDateTime getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(LocalDateTime flightDate) {
        this.flightDate = flightDate;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public BigDecimal getSeatPrice() {
        return seatPrice;
    }

    public void setSeatPrice(BigDecimal seatPrice) {
        this.seatPrice = seatPrice;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("fromAirport", fromAirport)
                .append("toAirport", toAirport)
                .append("flightDate", flightDate)
                .append("numberOfSeats", numberOfSeats)
                .append("seatPrice", seatPrice)
                .toString();
    }
}
