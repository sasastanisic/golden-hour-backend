package com.goldenhour.domain.landmark.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record LandmarkUpdateDTO(

        @NotBlank(message = "Name can't be blank")
        String name,

        @NotBlank(message = "Description can't be blank")
        String description,

        @Min(value = 0, message = "Minimum price is 0€")
        @Max(value = 1000, message = "Maximum price is 1000€")
        double price,

        @NotBlank(message = "Picture url can't be blank")
        String pictureUrl

) {

}
