package com.goldenhour.domain.hotel.service;

import com.goldenhour.domain.destination.service.DestinationService;
import com.goldenhour.domain.hotel.entity.Hotel;
import com.goldenhour.domain.hotel.model.HotelRequestDTO;
import com.goldenhour.domain.hotel.model.HotelResponseDTO;
import com.goldenhour.domain.hotel.model.HotelUpdateDTO;
import com.goldenhour.domain.hotel.repository.HotelRepository;
import com.goldenhour.infrastructure.handler.exceptions.ConflictException;
import com.goldenhour.infrastructure.handler.exceptions.NotFoundException;
import com.goldenhour.infrastructure.mapper.HotelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    private static final String HOTEL_NOT_EXISTS = "Hotel with id %d doesn't exist";

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final DestinationService destinationService;

    @Autowired
    public HotelServiceImpl(HotelRepository hotelRepository,
                            HotelMapper hotelMapper,
                            DestinationService destinationService) {
        this.hotelRepository = hotelRepository;
        this.hotelMapper = hotelMapper;
        this.destinationService = destinationService;
    }

    @Override
    public HotelResponseDTO createHotel(HotelRequestDTO hotelDTO) {
        Hotel hotel = hotelMapper.toHotel(hotelDTO);
        var destination = destinationService.getById(hotelDTO.destinationId());

        hotel.setDestination(destination);
        validateNameAndDestination(hotelDTO.name(), hotelDTO.destinationId());
        hotelRepository.save(hotel);

        return hotelMapper.toHotelResponseDTO(hotel);
    }

    private void validateNameAndDestination(String name, Long destinationId) {
        if (hotelRepository.existsByNameAndDestination_Id(name, destinationId)) {
            throw new ConflictException("%s already exists at destination that has id %d".formatted(name, destinationId));
        }
    }

    @Override
    public Page<HotelResponseDTO> getAllHotels(Pageable pageable) {
        return hotelRepository.findAll(pageable).map(hotelMapper::toHotelResponseDTO);
    }

    @Override
    public HotelResponseDTO getHotelById(Long id) {
        return hotelMapper.toHotelResponseDTO(getById(id));
    }

    public Hotel getById(Long id) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);

        if (optionalHotel.isEmpty()) {
            throw new NotFoundException(HOTEL_NOT_EXISTS.formatted(id));
        }

        return optionalHotel.get();
    }

    @Override
    public List<HotelResponseDTO> getHotelsByDestinationPlace(String destinationPlace) {
        List<Hotel> hotelsByDestinationPlace = hotelRepository.findByDestination_Place(destinationPlace);
        destinationService.existsByPlace(destinationPlace);

        if (hotelsByDestinationPlace.isEmpty()) {
            throw new NotFoundException("List of hotels in %s is empty".formatted(destinationPlace));
        }

        return hotelsByDestinationPlace
                .stream()
                .map(hotelMapper::toHotelResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public HotelResponseDTO updateHotel(Long id, HotelUpdateDTO hotelDTO) {
        Hotel hotel = getById(id);
        hotelMapper.updateHotelFromDTO(hotelDTO, hotel);

        hotelRepository.save(hotel);

        return hotelMapper.toHotelResponseDTO(hotel);
    }

    @Override
    public void updateNumberOfAvailableRooms(Hotel hotel) {
        hotel.setAvailableRooms(hotel.getAvailableRooms() - 1);
        hotelRepository.save(hotel);
    }

    @Override
    public void deleteHotel(Long id) {
        if (!hotelRepository.existsById(id)) {
            throw new NotFoundException(HOTEL_NOT_EXISTS.formatted(id));
        }

        hotelRepository.deleteById(id);
    }

}
