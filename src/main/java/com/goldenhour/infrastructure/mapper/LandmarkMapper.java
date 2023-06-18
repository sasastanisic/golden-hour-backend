package com.goldenhour.infrastructure.mapper;

import com.goldenhour.domain.landmark.entity.Landmark;
import com.goldenhour.domain.landmark.model.LandmarkRequestDTO;
import com.goldenhour.domain.landmark.model.LandmarkResponseDTO;
import com.goldenhour.domain.landmark.model.LandmarkUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LandmarkMapper {

    Landmark toLandmark(LandmarkRequestDTO landmarkRequestDTO);

    LandmarkResponseDTO toLandmarkResponseDTO(Landmark landmark);

    void updateLandmarkFromDTO(LandmarkUpdateDTO landmarkUpdateDTO, @MappingTarget Landmark landmark);

}
