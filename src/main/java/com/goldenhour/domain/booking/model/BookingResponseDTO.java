package com.goldenhour.domain.booking.model;

import com.goldenhour.domain.hotel.model.HotelResponseDTO;
import com.goldenhour.domain.travel.model.TravelResponseDTO;
import com.goldenhour.domain.user.model.UserResponseDTO;

public record BookingResponseDTO(

        Long id,
        int numberOfPeople,
        boolean ownTransport,
        double totalPrice,
        UserResponseDTO user,
        TravelResponseDTO travel,
        HotelResponseDTO hotel

) {

}
