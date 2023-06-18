package com.goldenhour.infrastructure.mapper;

import com.goldenhour.domain.hotel.entity.Hotel;
import com.goldenhour.domain.hotel.model.HotelRequestDTO;
import com.goldenhour.domain.hotel.model.HotelResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    Hotel toHotel(HotelRequestDTO hotelRequestDTO);

    HotelResponseDTO toHotelResponseDTO(Hotel hotel);

}
