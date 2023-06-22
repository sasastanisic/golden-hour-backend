package com.goldenhour.infrastructure.mapper;

import com.goldenhour.domain.travel.entity.Travel;
import com.goldenhour.domain.travel.model.TravelRequestDTO;
import com.goldenhour.domain.travel.model.TravelResponseDTO;
import com.goldenhour.domain.travel.model.TravelUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TravelMapper {

    Travel toTravel(TravelRequestDTO travelRequestDTO);

    TravelResponseDTO toTravelResponseDTO(Travel travel);

    void updateTravelFromDTO(TravelUpdateDTO travelUpdateDTO, @MappingTarget Travel travel);

}
