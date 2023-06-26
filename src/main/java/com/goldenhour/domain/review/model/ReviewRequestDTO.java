package com.goldenhour.domain.review.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReviewRequestDTO(

        @Min(value = 1, message = "Minimum value of rating is 1")
        @Max(value = 5, message = "Maximum value of rating is 5")
        int rating,

        String comment,

        @NotNull(message = "Travel can't be null")
        Long travelId,

        @NotNull(message = "User can't be null")
        Long userId

) {

}
