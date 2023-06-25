package com.goldenhour.domain.travel.repository;

import com.goldenhour.domain.travel.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Long> {

    boolean existsByNameAndDestination_Id(String name, Long destinationId);

    List<Travel> findByDestination_Id(Long destinationId);

}
