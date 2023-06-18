package com.goldenhour.domain.hotel.service;

import com.goldenhour.domain.destination.service.DestinationService;
import com.goldenhour.domain.hotel.entity.Hotel;
import com.goldenhour.domain.hotel.model.HotelRequestDTO;
import com.goldenhour.domain.hotel.model.HotelResponseDTO;
import com.goldenhour.domain.hotel.repository.HotelRepository;
import com.goldenhour.infrastructure.mapper.HotelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelServiceImpl implements HotelService {

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
        hotelRepository.save(hotel);

        return hotelMapper.toHotelResponseDTO(hotel);
    }

}
