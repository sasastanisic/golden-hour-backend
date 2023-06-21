package com.goldenhour.domain.landmark.service;

import com.goldenhour.domain.destination.service.DestinationService;
import com.goldenhour.domain.landmark.entity.Landmark;
import com.goldenhour.domain.landmark.model.LandmarkRequestDTO;
import com.goldenhour.domain.landmark.model.LandmarkResponseDTO;
import com.goldenhour.domain.landmark.model.LandmarkUpdateDTO;
import com.goldenhour.domain.landmark.repository.LandmarkRepository;
import com.goldenhour.infrastructure.handler.exceptions.ConflictException;
import com.goldenhour.infrastructure.handler.exceptions.NotFoundException;
import com.goldenhour.infrastructure.mapper.LandmarkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LandmarkServiceImpl implements LandmarkService {

    private static final String LANDMARK_NOT_EXISTS = "Landmark with id %d doesn't exist";

    private final LandmarkRepository landmarkRepository;
    private final LandmarkMapper landmarkMapper;
    private final DestinationService destinationService;

    @Autowired
    public LandmarkServiceImpl(LandmarkRepository landmarkRepository,
                               LandmarkMapper landmarkMapper,
                               DestinationService destinationService) {
        this.landmarkRepository = landmarkRepository;
        this.landmarkMapper = landmarkMapper;
        this.destinationService = destinationService;
    }

    @Override
    public LandmarkResponseDTO createLandmark(LandmarkRequestDTO landmarkDTO) {
        Landmark landmark = landmarkMapper.toLandmark(landmarkDTO);
        var destination = destinationService.getById(landmarkDTO.destinationId());

        landmark.setDestination(destination);
        validateNameAndDestination(landmarkDTO.name(), landmarkDTO.destinationId());
        landmarkRepository.save(landmark);

        return landmarkMapper.toLandmarkResponseDTO(landmark);
    }

    private void validateNameAndDestination(String name, Long destinationId) {
        if (landmarkRepository.existsByNameAndDestination_Id(name, destinationId)) {
            throw new ConflictException("%s already exists at destination that has id %d".formatted(name, destinationId));
        }
    }

    @Override
    public Page<LandmarkResponseDTO> getAllLandmarks(Pageable pageable) {
        return landmarkRepository.findAll(pageable).map(landmarkMapper::toLandmarkResponseDTO);
    }

    @Override
    public LandmarkResponseDTO getLandmarkById(Long id) {
        return landmarkMapper.toLandmarkResponseDTO(getById(id));
    }

    private Landmark getById(Long id) {
        Optional<Landmark> optionalLandmark = landmarkRepository.findById(id);

        if (optionalLandmark.isEmpty()) {
            throw new NotFoundException(LANDMARK_NOT_EXISTS.formatted(id));
        }

        return optionalLandmark.get();
    }

    @Override
    public List<LandmarkResponseDTO> getLandmarksByDestination(Long destinationId) {
        List<Landmark> landmarksByDestination = landmarkRepository.findByDestination_Id(destinationId);
        destinationService.existsById(destinationId);

        if (landmarksByDestination.isEmpty()) {
            throw new NotFoundException("List of landmarks by destination with id %d is empty".formatted(destinationId));
        }

        return landmarksByDestination
                .stream()
                .map(landmarkMapper::toLandmarkResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LandmarkResponseDTO updateLandmark(Long id, LandmarkUpdateDTO landmarkDTO) {
        Landmark landmark = getById(id);
        landmarkMapper.updateLandmarkFromDTO(landmarkDTO, landmark);

        landmarkRepository.save(landmark);

        return landmarkMapper.toLandmarkResponseDTO(landmark);
    }

    @Override
    public void deleteLandmark(Long id) {
        if (!landmarkRepository.existsById(id)) {
            throw new NotFoundException(LANDMARK_NOT_EXISTS.formatted(id));
        }

        landmarkRepository.deleteById(id);
    }

}
