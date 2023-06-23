package com.goldenhour.domain.destination.service;

import com.goldenhour.domain.destination.entity.Destination;
import com.goldenhour.domain.destination.model.DestinationRequestDTO;
import com.goldenhour.domain.destination.model.DestinationResponseDTO;
import com.goldenhour.domain.destination.model.DestinationUpdateDTO;
import com.goldenhour.domain.destination.repository.DestinationRepository;
import com.goldenhour.infrastructure.handler.exceptions.ConflictException;
import com.goldenhour.infrastructure.handler.exceptions.NotFoundException;
import com.goldenhour.infrastructure.mapper.DestinationMapperImpl;
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
public class DestinationServiceImplTest {

    Destination destination;

    Page<Destination> destinations;

    @InjectMocks
    DestinationServiceImpl destinationService;

    @Mock
    DestinationRepository destinationRepository;

    @Mock
    DestinationMapperImpl destinationMapper;

    @BeforeEach
    void setUp() {
        destination = new Destination();
        destination.setId(1L);
        destination.setPlace("Santorini");
        destination.setCountry("Greece");
        destination.setPopulation(15000);
        destination.setDescription("One of the most beautiful islands in the world");
        destination.setPictureUrl("santorini.jpg");

        List<Destination> destinationList = new ArrayList<>();
        destinationList.add(destination);
        destinations = new PageImpl<>(destinationList);
    }

    @Test
    void testCreateDestination() {
        DestinationRequestDTO destinationRequestDTO = new DestinationRequestDTO("Santorini", "Greece", 15000,
                "One of the most beautiful islands in the world", "santorini.jpg");
        DestinationResponseDTO destinationResponseDTO = new DestinationResponseDTO(1L, "Santorini", "Greece", 15000,
                "One of the most beautiful islands in the world", "santorini.jpg");

        when(destinationMapper.toDestination(destinationRequestDTO)).thenReturn(destination);
        when(destinationRepository.existsByCountryAndPlace(destinationRequestDTO.country(), destinationRequestDTO.place())).thenReturn(false);
        when(destinationRepository.save(destination)).thenReturn(destination);
        doReturn(destinationResponseDTO).when(destinationMapper).toDestinationResponseDTO(destination);

        var createdDestinationDTO = destinationService.createDestination(destinationRequestDTO);

        assertThat(destinationResponseDTO).usingRecursiveComparison().isEqualTo(createdDestinationDTO);
    }

    @Test
    void testDestinationByCountryAndPlace() {
        DestinationRequestDTO destinationRequestDTO = new DestinationRequestDTO("Santorini", "Greece", 15000,
                "One of the most beautiful islands in the world", "santorini.jpg");

        when(destinationMapper.toDestination(destinationRequestDTO)).thenReturn(destination);
        doReturn(false).when(destinationRepository).existsByCountryAndPlace(destinationRequestDTO.country(), destinationRequestDTO.place());

        Assertions.assertDoesNotThrow(() -> destinationService.createDestination(destinationRequestDTO));
    }

    @Test
    void testDestinationByCountryAndPlace_NotValid() {
        DestinationRequestDTO destinationRequestDTO = new DestinationRequestDTO("Santorini", "Greece", 15000,
                "One of the most beautiful islands in the world", "santorini.jpg");

        when(destinationMapper.toDestination(destinationRequestDTO)).thenReturn(destination);
        doReturn(true).when(destinationRepository).existsByCountryAndPlace(destinationRequestDTO.country(), destinationRequestDTO.place());

        Assertions.assertThrows(ConflictException.class, () -> destinationService.createDestination(destinationRequestDTO));
    }

    @Test
    void testGetAllDestinations() {
        Pageable pageable = mock(Pageable.class);
        DestinationResponseDTO destinationResponseDTO = new DestinationResponseDTO(1L, "Santorini", "Greece", 15000,
                "One of the most beautiful islands in the world", "santorini.jpg");

        when(destinationMapper.toDestinationResponseDTO(destination)).thenReturn(destinationResponseDTO);
        var expectedDestinations = destinations.map(destination -> destinationMapper.toDestinationResponseDTO(destination));
        doReturn(destinations).when(destinationRepository).findAll(pageable);
        var destinationPage = destinationService.getAllDestinations(pageable);

        Assertions.assertEquals(expectedDestinations, destinationPage);
    }

    @Test
    void testGetDestinationById() {
        DestinationResponseDTO destinationResponseDTO = new DestinationResponseDTO(1L, "Santorini", "Greece", 15000,
                "One of the most beautiful islands in the world", "santorini.jpg");

        when(destinationMapper.toDestinationResponseDTO(destination)).thenReturn(destinationResponseDTO);
        var expectedDestination = destinationMapper.toDestinationResponseDTO(destination);
        doReturn(Optional.of(destination)).when(destinationRepository).findById(1L);
        var returnedDestination = destinationService.getDestinationById(1L);

        Assertions.assertEquals(expectedDestination, returnedDestination);
    }

    @Test
    void testGetDestinationById_NotFound() {
        doReturn(Optional.empty()).when(destinationRepository).findById(1L);
        Assertions.assertThrows(NotFoundException.class, () -> destinationService.getDestinationById(1L));
    }

    @Test
    void testDestinationExists() {
        doReturn(true).when(destinationRepository).existsById(1L);
        Assertions.assertDoesNotThrow(() -> destinationService.existsById(1L));
        verify(destinationRepository, times(1)).existsById(1L);
    }

    @Test
    void testDestinationExists_NotFound() {
        doReturn(false).when(destinationRepository).existsById(1L);
        Assertions.assertThrows(NotFoundException.class, () -> destinationService.existsById(1L));
    }

    @Test
    void testDestinationExistsByPlace() {
        doReturn(true).when(destinationRepository).existsByPlace("Santorini");
        Assertions.assertDoesNotThrow(() -> destinationService.existsByPlace("Santorini"));
        verify(destinationRepository, times(1)).existsByPlace("Santorini");
    }

    @Test
    void testDestinationExistsByPlace_NotFound() {
        doReturn(false).when(destinationRepository).existsByPlace("Santorini");
        Assertions.assertThrows(NotFoundException.class, () -> destinationService.existsByPlace("Santorini"));
    }

    @Test
    void testGetDestinationsByCountry() {
        List<Destination> destinationsByCountry = List.of(destination);
        DestinationResponseDTO destinationResponseDTO = new DestinationResponseDTO(1L, "Santorini", "Greece", 15000,
                "One of the most beautiful islands in the world", "santorini.jpg");

        when(destinationMapper.toDestinationResponseDTO(destination)).thenReturn(destinationResponseDTO);
        var expectedList = destinationsByCountry
                .stream()
                .map(destinationMapper::toDestinationResponseDTO)
                .toList();
        doReturn(destinationsByCountry).when(destinationRepository).findByCountry(destination.getCountry());
        var returnedList = destinationService.getDestinationsByCountry(destination.getCountry());

        Assertions.assertTrue(destinationsByCountry.contains(destination));
        assertThat(expectedList).usingRecursiveComparison().isEqualTo(returnedList);
        Assertions.assertFalse(returnedList.isEmpty());
    }

    @Test
    void testUpdateDestination() {
        DestinationUpdateDTO destinationUpdateDTO = new DestinationUpdateDTO("Santorini", "Greece", 50000,
                "The most popular island in the Europe at the moment", "santorini.jpg");
        DestinationResponseDTO destinationResponseDTO = new DestinationResponseDTO(1L, "Santorini", "Greece", 50000,
                "The most popular island in the Europe at the moment", "santorini.jpg");

        when(destinationRepository.findById(1L)).thenReturn(Optional.of(destination));
        doCallRealMethod().when(destinationMapper).updateDestinationFromDTO(destinationUpdateDTO, destination);
        when(destinationRepository.existsByCountryAndPlace(destinationUpdateDTO.country(), destinationUpdateDTO.place())).thenReturn(false);
        when(destinationRepository.save(destination)).thenReturn(destination);
        doReturn(destinationResponseDTO).when(destinationMapper).toDestinationResponseDTO(destination);

        var updatedDestinationDTO = destinationService.updateDestination(1L, destinationUpdateDTO);

        assertThat(destinationResponseDTO).usingRecursiveComparison().isEqualTo(updatedDestinationDTO);
    }

    @Test
    void testDeleteDestination() {
        when(destinationRepository.existsById(1L)).thenReturn(true);
        doNothing().when(destinationRepository).deleteById(1L);
        Assertions.assertDoesNotThrow(() -> destinationService.deleteDestination(1L));
    }

    @Test
    void testDeleteDestination_NotFound() {
        doReturn(false).when(destinationRepository).existsById(1L);
        Assertions.assertThrows(NotFoundException.class, () -> destinationService.deleteDestination(1L));
    }

}
