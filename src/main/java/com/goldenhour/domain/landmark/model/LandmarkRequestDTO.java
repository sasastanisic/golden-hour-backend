package com.goldenhour.domain.landmark.model;

import com.goldenhour.domain.landmark.enums.Category;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LandmarkRequestDTO(

        @NotBlank(message = "Name can't be blank")
        String name,

        @NotBlank(message = "Description can't be blank")
        String description,

        @Min(value = 0, message = "Minimum price is 0€")
        @Max(value = 1000, message = "Maximum price is 1000€")
        double price,

        @NotNull(message = "Landmark category can't be null")
        Category category,

        @NotNull(message = "Destination can't be null")
        Long destinationId

) {

}
