package com.goldenhour.domain.destination.service;

import com.goldenhour.domain.destination.entity.Destination;
import com.goldenhour.domain.destination.model.DestinationRequestDTO;
import com.goldenhour.domain.destination.model.DestinationResponseDTO;
import com.goldenhour.domain.destination.model.DestinationUpdateDTO;
import com.goldenhour.domain.destination.repository.DestinationRepository;
import com.goldenhour.infrastructure.handler.exceptions.ConflictException;
import com.goldenhour.infrastructure.handler.exceptions.NotFoundException;
import com.goldenhour.infrastructure.mapper.DestinationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DestinationServiceImpl implements DestinationService {

    private static final String DESTINATION_NOT_EXISTS = "Destination with id %d doesn't exist";

    private final DestinationRepository destinationRepository;
    private final DestinationMapper destinationMapper;

    @Autowired
    public DestinationServiceImpl(DestinationRepository destinationRepository, DestinationMapper destinationMapper) {
        this.destinationRepository = destinationRepository;
        this.destinationMapper = destinationMapper;
    }

    @Override
    public DestinationResponseDTO createDestination(DestinationRequestDTO destinationDTO) {
        Destination destination = destinationMapper.toDestination(destinationDTO);

        validateCountryAndPlace(destinationDTO.country(), destinationDTO.place());
        destinationRepository.save(destination);

        return destinationMapper.toDestinationResponseDTO(destination);
    }

    private void validateCountryAndPlace(String country, String place) {
        if (destinationRepository.existsByCountryAndPlace(country, place)) {
            throw new ConflictException("Place %s already exists in country %s".formatted(place, country));
        }
    }

    @Override
    public Page<DestinationResponseDTO> getAllDestinations(Pageable pageable) {
        return destinationRepository.findAll(pageable).map(destinationMapper::toDestinationResponseDTO);
    }

    @Override
    public DestinationResponseDTO getDestinationById(Long id) {
        return destinationMapper.toDestinationResponseDTO(getById(id));
    }

    private Destination getById(Long id) {
        Optional<Destination> optionalDestination = destinationRepository.findById(id);

        if (optionalDestination.isEmpty()) {
            throw new NotFoundException(DESTINATION_NOT_EXISTS.formatted(id));
        }

        return optionalDestination.get();
    }

    @Override
    public List<DestinationResponseDTO> getDestinationsByCountry(String country) {
        List<Destination> destinationsByCountry = destinationRepository.findByCountry(country);

        if (destinationsByCountry.isEmpty()) {
            throw new NotFoundException("There are 0 destinations in country %s".formatted(country));
        }

        return destinationsByCountry
                .stream()
                .map(destinationMapper::toDestinationResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DestinationResponseDTO updateDestination(Long id, DestinationUpdateDTO destinationDTO) {
        Destination destination = getById(id);
        destinationMapper.updateDestinationFromDTO(destinationDTO, destination);

        validateCountryAndPlace(destinationDTO.country(), destinationDTO.place());
        destinationRepository.save(destination);

        return destinationMapper.toDestinationResponseDTO(destination);
    }

    @Override
    public void deleteDestination(Long id) {
        if (!destinationRepository.existsById(id)) {
            throw new NotFoundException(DESTINATION_NOT_EXISTS.formatted(id));
        }

        destinationRepository.deleteById(id);
    }

}
