package com.goldenhour.domain.travel.repository;

import com.goldenhour.domain.travel.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Long> {
}
