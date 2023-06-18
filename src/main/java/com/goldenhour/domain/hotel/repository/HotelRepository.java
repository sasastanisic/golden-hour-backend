package com.goldenhour.domain.hotel.repository;

import com.goldenhour.domain.hotel.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    boolean existsByNameAndDestination_Id(String name, Long destinationId);

    List<Hotel> findByDestination_Place(String destinationPlace);

}
