package com.goldenhour.domain.destination.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record DestinationRequestDTO(

        @NotBlank(message = "Place can't be blank")
        String place,

        @NotBlank(message = "Country can't be blank")
        String country,

        @Min(value = 1000, message = "Population should have a value of at least 1000")
        double population,

        @NotBlank(message = "Description can't be blank")
        String description,

        @NotBlank(message = "Picture url can't be blank")
        String pictureUrl

) {

}
