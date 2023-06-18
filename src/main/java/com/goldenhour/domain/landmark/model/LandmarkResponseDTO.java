package com.goldenhour.domain.landmark.model;

import com.goldenhour.domain.destination.model.DestinationResponseDTO;
import com.goldenhour.domain.landmark.enums.Category;

public record LandmarkResponseDTO(

        Long id,
        String name,
        String description,
        double price,
        Category category,
        DestinationResponseDTO destination

) {

}
