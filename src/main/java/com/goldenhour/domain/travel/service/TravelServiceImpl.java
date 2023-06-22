package com.goldenhour.domain.travel.service;

import com.goldenhour.domain.travel.repository.TravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TravelServiceImpl implements TravelService {

    private final TravelRepository travelRepository;

    @Autowired
    public TravelServiceImpl(TravelRepository travelRepository) {
        this.travelRepository = travelRepository;
    }

}
