package com.goldenhour.domain.hotel.service;

import com.goldenhour.domain.destination.entity.Destination;
import com.goldenhour.domain.destination.model.DestinationResponseDTO;
import com.goldenhour.domain.destination.service.DestinationService;
import com.goldenhour.domain.hotel.entity.Hotel;
import com.goldenhour.domain.hotel.enums.HotelType;
import com.goldenhour.domain.hotel.model.HotelRequestDTO;
import com.goldenhour.domain.hotel.model.HotelResponseDTO;
import com.goldenhour.domain.hotel.model.HotelUpdateDTO;
import com.goldenhour.domain.hotel.repository.HotelRepository;
import com.goldenhour.infrastructure.handler.exceptions.NotFoundException;
import com.goldenhour.infrastructure.mapper.HotelMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HotelServiceImplTest {

    Hotel hotel;

    Destination destination;

    Page<Hotel> hotels;

    @InjectMocks
    HotelServiceImpl hotelService;

    @Mock
    HotelRepository hotelRepository;

    @Mock
    HotelMapperImpl hotelMapper;

    @Mock
    DestinationService destinationService;

    @BeforeEach
    void setUp() {
        destination = new Destination();
        destination.setId(1L);
        destination.setPlace("Santorini");
        destination.setCountry("Greece");
        destination.setPopulation(15000);
        destination.setDescription("One of the most beautiful islands in the world");
        destination.setPictureUrl("santorini.jpg");

        hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Astarte Suites");
        hotel.setStars(5);
        hotel.setHotelType(HotelType.HOTEL);
        hotel.setAvailableRooms(30);
        hotel.setPricePerNight(250);
        hotel.setPictureUrl("astarte-suites.png");
        hotel.setDestination(destination);

        List<Hotel> hotelList = new ArrayList<>();
        hotelList.add(hotel);
        hotels = new PageImpl<>(hotelList);
    }

    @Test
    void testCreateHotel() {
        HotelRequestDTO hotelRequestDTO = new HotelRequestDTO("Astarte Suites", 5, HotelType.HOTEL, 30, 250,
                "astarte-suites.png", 1L);
        DestinationResponseDTO destinationResponseDTO = new DestinationResponseDTO(1L, "Santorini", "Greece", 15000,
                "One of the most beautiful islands in the world", "santorini.jpg");
        HotelResponseDTO hotelResponseDTO = new HotelResponseDTO(1L, "Astarte Suites", 5, HotelType.HOTEL, 30, 250,
                "astarte-suites.png", destinationResponseDTO);

        when(hotelMapper.toHotel(hotelRequestDTO)).thenReturn(hotel);
        when(destinationService.getById(destination.getId())).thenReturn(hotel.getDestination());
        when(hotelRepository.existsByNameAndDestination_Id(hotelRequestDTO.name(), hotelRequestDTO.destinationId())).thenReturn(false);
        when(hotelRepository.save(hotel)).thenReturn(hotel);
        doReturn(hotelResponseDTO).when(hotelMapper).toHotelResponseDTO(hotel);

        var createdHotelDTO = hotelService.createHotel(hotelRequestDTO);

        Assertions.assertEquals(hotelResponseDTO, createdHotelDTO);
    }

    @Test
    void testGetAllHotels() {
        Pageable pageable = mock(Pageable.class);
        DestinationResponseDTO destinationResponseDTO = new DestinationResponseDTO(1L, "Santorini", "Greece", 15000,
                "One of the most beautiful islands in the world", "santorini.jpg");
        HotelResponseDTO hotelResponseDTO = new HotelResponseDTO(1L, "Astarte Suites", 5, HotelType.HOTEL, 30, 250,
                "astarte-suites.png", destinationResponseDTO);

        when(hotelMapper.toHotelResponseDTO(hotel)).thenReturn(hotelResponseDTO);
        var expectedHotels = hotels.map(hotel -> hotelMapper.toHotelResponseDTO(hotel));
        doReturn(hotels).when(hotelRepository).findAll(pageable);
        var hotelPage = hotelService.getAllHotels(pageable);

        Assertions.assertEquals(expectedHotels, hotelPage);
    }

    @Test
    void testGetHotelById() {
        DestinationResponseDTO destinationResponseDTO = new DestinationResponseDTO(1L, "Santorini", "Greece", 15000,
                "One of the most beautiful islands in the world", "santorini.jpg");
        HotelResponseDTO hotelResponseDTO = new HotelResponseDTO(1L, "Astarte Suites", 5, HotelType.HOTEL, 30, 250,
                "astarte-suites.png", destinationResponseDTO);

        when(hotelMapper.toHotelResponseDTO(hotel)).thenReturn(hotelResponseDTO);
        var expectedHotel = hotelMapper.toHotelResponseDTO(hotel);
        doReturn(Optional.of(hotel)).when(hotelRepository).findById(1L);
        var returnedHotel = hotelService.getHotelById(1L);

        assertThat(expectedHotel).usingRecursiveComparison().isEqualTo(returnedHotel);
    }

    @Test
    void testGetHotelById_NotFound() {
        doReturn(Optional.empty()).when(hotelRepository).findById(1L);
        Assertions.assertThrows(NotFoundException.class, () -> hotelService.getHotelById(1L));
    }

    @Test
    void testUpdateHotel() {
        HotelUpdateDTO hotelUpdateDTO = new HotelUpdateDTO("Astarte Suites", 5, HotelType.RESORT, 50, 400,
                "astarte-suites.png");
        DestinationResponseDTO destinationResponseDTO = new DestinationResponseDTO(1L, "Santorini", "Greece", 15000,
                "One of the most beautiful islands in the world", "santorini.jpg");
        HotelResponseDTO hotelResponseDTO = new HotelResponseDTO(1L, "Astarte Suites", 5, HotelType.RESORT, 50, 400,
                "astarte-suites.png", destinationResponseDTO);

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        doCallRealMethod().when(hotelMapper).updateHotelFromDTO(hotelUpdateDTO, hotel);
        when(hotelRepository.save(hotel)).thenReturn(hotel);
        doReturn(hotelResponseDTO).when(hotelMapper).toHotelResponseDTO(hotel);

        var updatedHotelDTO = hotelService.updateHotel(1L, hotelUpdateDTO);

        Assertions.assertEquals(hotelResponseDTO, updatedHotelDTO);
    }

    @Test
    void testDeleteHotel() {
        when(hotelRepository.existsById(1L)).thenReturn(true);
        doNothing().when(hotelRepository).deleteById(1L);
        Assertions.assertDoesNotThrow(() -> hotelService.deleteHotel(1L));
    }

    @Test
    void testDeleteHotel_NotFound() {
        doReturn(false).when(hotelRepository).existsById(1L);
        Assertions.assertThrows(NotFoundException.class, () -> hotelService.deleteHotel(1L));
    }

}
