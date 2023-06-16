package com.goldenhour.web;

import com.goldenhour.domain.destination.model.DestinationRequestDTO;
import com.goldenhour.domain.destination.model.DestinationResponseDTO;
import com.goldenhour.domain.destination.model.DestinationUpdateDTO;
import com.goldenhour.domain.destination.service.DestinationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinations")
public class DestinationController {

    private final DestinationService destinationService;

    @Autowired
    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @PostMapping
    public ResponseEntity<DestinationResponseDTO> createDestination(@Valid @RequestBody DestinationRequestDTO destinationDTO) {
        return ResponseEntity.ok(destinationService.createDestination(destinationDTO));
    }

    @GetMapping
    public ResponseEntity<Page<DestinationResponseDTO>> getAllDestinations(Pageable pageable) {
        return ResponseEntity.ok(destinationService.getAllDestinations(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DestinationResponseDTO> getDestinationById(@PathVariable Long id) {
        return ResponseEntity.ok(destinationService.getDestinationById(id));
    }

    @GetMapping("/by-country")
    public ResponseEntity<List<DestinationResponseDTO>> getDestinationsByCountry(@RequestParam String country) {
        return ResponseEntity.ok(destinationService.getDestinationsByCountry(country));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DestinationResponseDTO> updateDestination(@PathVariable Long id, @Valid @RequestBody DestinationUpdateDTO destinationDTO) {
        return ResponseEntity.ok(destinationService.updateDestination(id, destinationDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDestination(@PathVariable Long id) {
        destinationService.deleteDestination(id);
    }

}
