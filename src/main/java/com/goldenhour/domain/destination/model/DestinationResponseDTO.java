package com.goldenhour.domain.destination.model;

public record DestinationResponseDTO(

        Long id,
        String place,
        String country,
        double population,
        String description

) {

}
