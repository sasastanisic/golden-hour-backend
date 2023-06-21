package com.goldenhour.domain.landmark.repository;

import com.goldenhour.domain.landmark.entity.Landmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LandmarkRepository extends JpaRepository<Landmark, Long> {

    boolean existsByNameAndDestination_Id(String name, Long destinationId);

    List<Landmark> findByDestination_Id(Long destinationId);

}
