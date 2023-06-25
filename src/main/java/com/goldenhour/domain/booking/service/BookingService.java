package com.goldenhour.domain.booking.service;

import com.goldenhour.domain.booking.model.BookingRequestDTO;
import com.goldenhour.domain.booking.model.BookingResponseDTO;
import com.goldenhour.domain.booking.model.BookingUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookingService {

    BookingResponseDTO createBooking(BookingRequestDTO bookingDTO);

    Page<BookingResponseDTO> getAllBookings(Pageable pageable);

    BookingResponseDTO getBookingById(Long id);

    List<BookingResponseDTO> getBookingsByUser(Long userId);

    List<BookingResponseDTO> getBookingsByTravel(Long travelId);

    List<BookingResponseDTO> getBookingsByHotel(Long hotelId);

    BookingResponseDTO updateBooking(Long id, BookingUpdateDTO bookingDTO);

    void deleteBooking(Long id);

}
