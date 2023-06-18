package com.goldenhour.domain.destination.service;

import com.goldenhour.domain.destination.entity.Destination;
import com.goldenhour.domain.destination.model.DestinationRequestDTO;
import com.goldenhour.domain.destination.model.DestinationResponseDTO;
import com.goldenhour.domain.destination.model.DestinationUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DestinationService {

    DestinationResponseDTO createDestination(DestinationRequestDTO destinationDTO);

    Page<DestinationResponseDTO> getAllDestinations(Pageable pageable);

    DestinationResponseDTO getDestinationById(Long id);

    Destination getById(Long id);

    List<DestinationResponseDTO> getDestinationsByCountry(String country);

    DestinationResponseDTO updateDestination(Long id, DestinationUpdateDTO destinationDTO);

    void deleteDestination(Long id);

}
