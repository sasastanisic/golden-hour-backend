package com.goldenhour.infrastructure.mapper;

import com.goldenhour.domain.destination.entity.Destination;
import com.goldenhour.domain.destination.model.DestinationRequestDTO;
import com.goldenhour.domain.destination.model.DestinationResponseDTO;
import com.goldenhour.domain.destination.model.DestinationUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DestinationMapper {

    Destination toDestination(DestinationRequestDTO destinationRequestDTO);

    DestinationResponseDTO toDestinationResponseDTO(Destination destination);

    void updateDestinationFromDTO(DestinationUpdateDTO destinationUpdateDTO, @MappingTarget Destination destination);

}
