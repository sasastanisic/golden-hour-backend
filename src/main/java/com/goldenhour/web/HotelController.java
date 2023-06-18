package com.goldenhour.web;

import com.goldenhour.domain.hotel.model.HotelRequestDTO;
import com.goldenhour.domain.hotel.model.HotelResponseDTO;
import com.goldenhour.domain.hotel.model.HotelUpdateDTO;
import com.goldenhour.domain.hotel.service.HotelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping
    public ResponseEntity<HotelResponseDTO> createHotel(@Valid @RequestBody HotelRequestDTO hotelDTO) {
        return ResponseEntity.ok(hotelService.createHotel(hotelDTO));
    }

    @GetMapping
    public ResponseEntity<Page<HotelResponseDTO>> getAllHotels(Pageable pageable) {
        return ResponseEntity.ok(hotelService.getAllHotels(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponseDTO> getHotelById(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.getHotelById(id));
    }

    @GetMapping("/by-destination-place")
    public ResponseEntity<List<HotelResponseDTO>> getHotelsByDestinationPlace(@RequestParam String destinationPlace) {
        return ResponseEntity.ok(hotelService.getHotelsByDestinationPlace(destinationPlace));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelResponseDTO> updateHotel(@PathVariable Long id, @Valid @RequestBody HotelUpdateDTO hotelDTO) {
        return ResponseEntity.ok(hotelService.updateHotel(id, hotelDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
    }

}
