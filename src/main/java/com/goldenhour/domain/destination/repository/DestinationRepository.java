package com.goldenhour.domain.destination.repository;

import com.goldenhour.domain.destination.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {

    boolean existsByPlace(String place);

    boolean existsByCountryAndPlace(String country, String place);

    List<Destination> findByCountry(String country);

}
