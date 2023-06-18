package com.goldenhour.domain.hotel.service;

import com.goldenhour.domain.hotel.model.HotelRequestDTO;
import com.goldenhour.domain.hotel.model.HotelResponseDTO;
import com.goldenhour.domain.hotel.model.HotelUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HotelService {

    HotelResponseDTO createHotel(HotelRequestDTO hotelDTO);

    Page<HotelResponseDTO> getAllHotels(Pageable pageable);

    HotelResponseDTO getHotelById(Long id);

    List<HotelResponseDTO> getHotelsByDestinationPlace(String destinationPlace);

    HotelResponseDTO updateHotel(Long id, HotelUpdateDTO hotelDTO);

    void deleteHotel(Long id);

}
