package com.goldenhour.domain.travel.service;

import com.goldenhour.domain.destination.service.DestinationService;
import com.goldenhour.domain.travel.entity.Travel;
import com.goldenhour.domain.travel.model.TravelRequestDTO;
import com.goldenhour.domain.travel.model.TravelResponseDTO;
import com.goldenhour.domain.travel.model.TravelUpdateDTO;
import com.goldenhour.domain.travel.repository.TravelRepository;
import com.goldenhour.infrastructure.handler.exceptions.ConflictException;
import com.goldenhour.infrastructure.handler.exceptions.NotFoundException;
import com.goldenhour.infrastructure.mapper.TravelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TravelServiceImpl implements TravelService {

    private final static String TRAVEL_NOT_EXISTS = "Travel with id %d doesn't exist";

    private final TravelRepository travelRepository;
    private final TravelMapper travelMapper;
    private final DestinationService destinationService;

    @Autowired
    public TravelServiceImpl(TravelRepository travelRepository,
                             TravelMapper travelMapper,
                             DestinationService destinationService) {
        this.travelRepository = travelRepository;
        this.travelMapper = travelMapper;
        this.destinationService = destinationService;
    }

    @Override
    public TravelResponseDTO createTravel(TravelRequestDTO travelDTO) {
        Travel travel = travelMapper.toTravel(travelDTO);
        var destination = destinationService.getById(travelDTO.destinationId());

        travel.setDestination(destination);
        validateNameAndDestination(travelDTO.name(), travelDTO.destinationId());
        travelRepository.save(travel);

        return travelMapper.toTravelResponseDTO(travel);
    }

    private void validateNameAndDestination(String name, Long destinationId) {
        if (travelRepository.existsByNameAndDestination_Id(name, destinationId)) {
            throw new ConflictException("%s already exists at destination that has id %d".formatted(name, destinationId));
        }
    }

    @Override
    public Page<TravelResponseDTO> getAllTravels(Pageable pageable) {
        return travelRepository.findAll(pageable).map(travelMapper::toTravelResponseDTO);
    }

    @Override
    public TravelResponseDTO getTravelById(Long id) {
        return travelMapper.toTravelResponseDTO(getById(id));
    }

    public Travel getById(Long id) {
        Optional<Travel> optionalTravel = travelRepository.findById(id);

        if (optionalTravel.isEmpty()) {
            throw new NotFoundException(TRAVEL_NOT_EXISTS.formatted(id));
        }

        return optionalTravel.get();
    }

    @Override
    public List<TravelResponseDTO> getTravelsByDestination(Long destinationId) {
        List<Travel> travelsByDestination = travelRepository.findByDestination_Id(destinationId);
        destinationService.existsById(destinationId);

        if (travelsByDestination.isEmpty()) {
            throw new NotFoundException("List of travels by destination with id %d is empty".formatted(destinationId));
        }

        return travelsByDestination
                .stream()
                .map(travelMapper::toTravelResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TravelResponseDTO updateTravel(Long id, TravelUpdateDTO travelDTO) {
        Travel travel = getById(id);
        travelMapper.updateTravelFromDTO(travelDTO, travel);

        travelRepository.save(travel);

        return travelMapper.toTravelResponseDTO(travel);
    }

    @Override
    public void deleteTravel(Long id) {
        if (!travelRepository.existsById(id)) {
            throw new NotFoundException(TRAVEL_NOT_EXISTS.formatted(id));
        }

        travelRepository.deleteById(id);
    }

}
