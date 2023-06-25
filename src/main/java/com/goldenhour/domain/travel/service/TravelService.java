package com.goldenhour.domain.travel.service;

import com.goldenhour.domain.travel.entity.Travel;
import com.goldenhour.domain.travel.model.TravelRequestDTO;
import com.goldenhour.domain.travel.model.TravelResponseDTO;
import com.goldenhour.domain.travel.model.TravelUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TravelService {

    TravelResponseDTO createTravel(TravelRequestDTO travelDTO);

    Page<TravelResponseDTO> getAllTravels(Pageable pageable);

    TravelResponseDTO getTravelById(Long id);

    Travel getById(Long id);

    List<TravelResponseDTO> getTravelsByDestination(Long destinationId);

    TravelResponseDTO updateTravel(Long id, TravelUpdateDTO travelDTO);

    void deleteTravel(Long id);

}
