package com.goldenhour.domain.landmark.service;

import com.goldenhour.domain.destination.entity.Destination;
import com.goldenhour.domain.destination.service.DestinationService;
import com.goldenhour.domain.landmark.entity.Landmark;
import com.goldenhour.domain.landmark.enums.Category;
import com.goldenhour.domain.landmark.repository.LandmarkRepository;
import com.goldenhour.infrastructure.mapper.LandmarkMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class LandmarkServiceImplTest {

    Landmark landmark;

    Destination destination;

    Page<Landmark> landmarks;

    @InjectMocks
    LandmarkServiceImpl landmarkService;

    @Mock
    LandmarkRepository landmarkRepository;

    @Mock
    LandmarkMapperImpl landmarkMapper;

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

        landmark = new Landmark();
        landmark.setId(1L);
        landmark.setName("Sunset in Oia");
        landmark.setDescription("You can’t miss the sunset while in Santorini and Oia is a perfect place to experience it!");
        landmark.setPrice(0);
        landmark.setCategory(Category.NATURE);
        landmark.setPictureUrl("sunset-in-oia.png");
        landmark.setDestination(destination);

        List<Landmark> landmarkList = new ArrayList<>();
        landmarkList.add(landmark);
        landmarks = new PageImpl<>(landmarkList);
    }

    @Test
    void testCreateLandmark() {

    }

}
