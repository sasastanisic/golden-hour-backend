package com.goldenhour.infrastructure.mapper;

import com.goldenhour.domain.hotel.entity.Hotel;
import com.goldenhour.domain.hotel.model.HotelRequestDTO;
import com.goldenhour.domain.hotel.model.HotelResponseDTO;
import com.goldenhour.domain.hotel.model.HotelUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    Hotel toHotel(HotelRequestDTO hotelRequestDTO);

    HotelResponseDTO toHotelResponseDTO(Hotel hotel);

    void updateHotelFromDTO(HotelUpdateDTO hotelUpdateDTO, @MappingTarget Hotel hotel);

}
