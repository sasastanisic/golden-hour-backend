package com.goldenhour.web;

import com.goldenhour.domain.travel.model.TravelRequestDTO;
import com.goldenhour.domain.travel.model.TravelResponseDTO;
import com.goldenhour.domain.travel.model.TravelUpdateDTO;
import com.goldenhour.domain.travel.service.TravelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/travels")
public class TravelController {

    private final TravelService travelService;

    @Autowired
    public TravelController(TravelService travelService) {
        this.travelService = travelService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TravelResponseDTO> createTravel(@Valid @RequestBody TravelRequestDTO travelDTO) {
        return ResponseEntity.ok(travelService.createTravel(travelDTO));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<Page<TravelResponseDTO>> getAllTravels(Pageable pageable) {
        return ResponseEntity.ok(travelService.getAllTravels(pageable));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<TravelResponseDTO> getTravelById(@PathVariable Long id) {
        return ResponseEntity.ok(travelService.getTravelById(id));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/by-destination/{destinationId}")
    public ResponseEntity<List<TravelResponseDTO>> getTravelsByDestination(@PathVariable Long destinationId) {
        return ResponseEntity.ok(travelService.getTravelsByDestination(destinationId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TravelResponseDTO> updateTravel(@PathVariable Long id, @Valid @RequestBody TravelUpdateDTO travelDTO) {
        return ResponseEntity.ok(travelService.updateTravel(id, travelDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTravel(@PathVariable Long id) {
        travelService.deleteTravel(id);
    }

}
