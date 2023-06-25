package com.goldenhour.web;

import com.goldenhour.domain.booking.model.BookingRequestDTO;
import com.goldenhour.domain.booking.model.BookingResponseDTO;
import com.goldenhour.domain.booking.model.BookingUpdateDTO;
import com.goldenhour.domain.booking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(@Valid @RequestBody BookingRequestDTO bookingDTO) {
        return ResponseEntity.ok(bookingService.createBooking(bookingDTO));
    }

    @GetMapping
    public ResponseEntity<Page<BookingResponseDTO>> getAllBookings(Pageable pageable) {
        return ResponseEntity.ok(bookingService.getAllBookings(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.getBookingsByUser(userId));
    }

    @GetMapping("/by-travel/{travelId}")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByTravel(@PathVariable Long travelId) {
        return ResponseEntity.ok(bookingService.getBookingsByTravel(travelId));
    }

    @GetMapping("/by-hotel/{hotelId}")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByHotel(@PathVariable Long hotelId) {
        return ResponseEntity.ok(bookingService.getBookingsByHotel(hotelId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> updateBooking(@PathVariable Long id, @Valid @RequestBody BookingUpdateDTO bookingDTO) {
        return ResponseEntity.ok(bookingService.updateBooking(id, bookingDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
    }

}
