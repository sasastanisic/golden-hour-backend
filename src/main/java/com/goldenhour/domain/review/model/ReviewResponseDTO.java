package com.goldenhour.domain.review.model;

import com.goldenhour.domain.travel.model.TravelResponseDTO;
import com.goldenhour.domain.user.model.UserResponseDTO;

public record ReviewResponseDTO(

        Long id,
        int rating,
        String comment,
        TravelResponseDTO travel,
        UserResponseDTO user

) {

}
