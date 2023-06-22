package com.goldenhour.domain.travel.model;

import com.goldenhour.domain.destination.model.DestinationResponseDTO;
import com.goldenhour.domain.travel.enums.TransportType;
import com.goldenhour.domain.travel.enums.TravelDuration;

import java.time.LocalDate;

public record TravelResponseDTO(

        Long id,
        String name,
        LocalDate departureDay,
        TravelDuration travelDuration,
        TransportType transportType,
        int numberOfNights,
        DestinationResponseDTO destination

) {

}
