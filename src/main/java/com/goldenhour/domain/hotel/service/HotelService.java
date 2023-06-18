package com.goldenhour.domain.hotel.service;

import com.goldenhour.domain.hotel.model.HotelRequestDTO;
import com.goldenhour.domain.hotel.model.HotelResponseDTO;

public interface HotelService {

    HotelResponseDTO createHotel(HotelRequestDTO hotelDTO);

}
