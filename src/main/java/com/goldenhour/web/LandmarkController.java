package com.goldenhour.web;

import com.goldenhour.domain.landmark.model.LandmarkRequestDTO;
import com.goldenhour.domain.landmark.model.LandmarkResponseDTO;
import com.goldenhour.domain.landmark.model.LandmarkUpdateDTO;
import com.goldenhour.domain.landmark.service.LandmarkService;
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
@RequestMapping("/api/landmarks")
public class LandmarkController {

    private final LandmarkService landmarkService;

    @Autowired
    public LandmarkController(LandmarkService landmarkService) {
        this.landmarkService = landmarkService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<LandmarkResponseDTO> createLandmark(@Valid @RequestBody LandmarkRequestDTO landmarkDTO) {
        return ResponseEntity.ok(landmarkService.createLandmark(landmarkDTO));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<Page<LandmarkResponseDTO>> getAllLandmarks(Pageable pageable) {
        return ResponseEntity.ok(landmarkService.getAllLandmarks(pageable));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<LandmarkResponseDTO> getLandmarkById(@PathVariable Long id) {
        return ResponseEntity.ok(landmarkService.getLandmarkById(id));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/by-destination/{destinationId}")
    public ResponseEntity<List<LandmarkResponseDTO>> getLandmarksByDestination(@PathVariable Long destinationId) {
        return ResponseEntity.ok(landmarkService.getLandmarksByDestination(destinationId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<LandmarkResponseDTO> updateLandmark(@PathVariable Long id, @Valid @RequestBody LandmarkUpdateDTO landmarkDTO) {
        return ResponseEntity.ok(landmarkService.updateLandmark(id, landmarkDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLandmark(@PathVariable Long id) {
        landmarkService.deleteLandmark(id);
    }

}
