package com.goldenhour.domain.travel.model;

import com.goldenhour.domain.travel.enums.TransportType;
import com.goldenhour.domain.travel.enums.TravelDuration;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record TravelRequestDTO(

        @NotBlank(message = "Name can't be blank")
        String name,

        @NotNull(message = "Departure day can't be null")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate departureDay,

        @NotNull(message = "Travel duration can't be null")
        TravelDuration travelDuration,

        @NotNull(message = "Transport type can't be null")
        TransportType transportType,

        @Min(value = 0, message = "Number of nights can't be negative")
        @Max(value = 30, message = "Maximum number of nights is 30")
        int numberOfNights,

        @NotNull(message = "Destination can't be null")
        Long destinationId

) {

}
