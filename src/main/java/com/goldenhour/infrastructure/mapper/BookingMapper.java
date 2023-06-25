package com.goldenhour.infrastructure.mapper;

import com.goldenhour.domain.booking.entity.Booking;
import com.goldenhour.domain.booking.model.BookingRequestDTO;
import com.goldenhour.domain.booking.model.BookingResponseDTO;
import com.goldenhour.domain.booking.model.BookingUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    Booking toBooking(BookingRequestDTO bookingRequestDTO);

    BookingResponseDTO toBookingResponseDTO(Booking booking);

    void updateBookingFromDTO(BookingUpdateDTO bookingUpdateDTO, @MappingTarget Booking booking);

}
