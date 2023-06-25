package com.goldenhour.domain.booking.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record BookingUpdateDTO(

        @Min(value = 1, message = "Minimum number of people for booking a trip is 1")
        @Max(value = 6, message = "Maximum number of people for booking a trip is 6")
        int numberOfPeople,

        @NotNull(message = "Own transport can't be null")
        boolean ownTransport,

        @NotNull(message = "Hotel can't be null")
        Long hotelId

) {

}
