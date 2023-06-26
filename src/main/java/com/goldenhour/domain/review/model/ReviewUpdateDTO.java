package com.goldenhour.domain.review.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record ReviewUpdateDTO(

        @Min(value = 1, message = "Minimum value of rating is 1")
        @Max(value = 5, message = "Maximum value of rating is 5")
        int rating,

        String comment

) {

}
