package com.goldenhour.domain.landmark.service;

import com.goldenhour.domain.landmark.model.LandmarkRequestDTO;
import com.goldenhour.domain.landmark.model.LandmarkResponseDTO;
import com.goldenhour.domain.landmark.model.LandmarkUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LandmarkService {

    LandmarkResponseDTO createLandmark(LandmarkRequestDTO landmarkDTO);

    Page<LandmarkResponseDTO> getAllLandmarks(Pageable pageable);

    LandmarkResponseDTO getLandmarkById(Long id);

    List<LandmarkResponseDTO> getLandmarksByDestination(Long destinationId);

    LandmarkResponseDTO updateLandmark(Long id, LandmarkUpdateDTO landmarkDTO);

    void deleteLandmark(Long id);

}
