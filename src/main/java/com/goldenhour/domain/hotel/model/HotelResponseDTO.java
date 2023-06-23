package com.goldenhour.domain.hotel.model;

import com.goldenhour.domain.destination.model.DestinationResponseDTO;
import com.goldenhour.domain.hotel.enums.HotelType;

public record HotelResponseDTO(

        Long id,
        String name,
        int stars,
        HotelType hotelType,
        int availableRooms,
        double pricePerNight,
        String pictureUrl,
        DestinationResponseDTO destination

) {

}
