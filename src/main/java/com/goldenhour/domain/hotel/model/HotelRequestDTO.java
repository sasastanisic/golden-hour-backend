package com.goldenhour.domain.hotel.model;

import com.goldenhour.domain.hotel.enums.HotelType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HotelRequestDTO(

        @NotBlank(message = "Name can't be blank")
        String name,

        @Min(value = 1, message = "Minimum number of stars is 1")
        @Max(value = 5, message = "Maximum number of stars is 5")
        int stars,

        @NotNull(message = "Hotel type can't be null")
        HotelType hotelType,

        @Min(value = 0, message = "Value of available rooms can't be negative")
        @Max(value = 300, message = "Maximum value of available rooms is 300")
        int availableRooms,

        @Min(value = 5, message = "Minimum price for one night is 5€")
        @Max(value = 20000, message = "Maximum price for one night is 20000€")
        double pricePerNight,

        @NotBlank(message = "Picture url can't be blank")
        String pictureUrl,

        @NotNull(message = "Destination can't be null")
        Long destinationId

) {

}
