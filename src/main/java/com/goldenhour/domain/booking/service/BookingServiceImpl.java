package com.goldenhour.domain.booking.service;

import com.goldenhour.domain.booking.entity.Booking;
import com.goldenhour.domain.booking.model.BookingRequestDTO;
import com.goldenhour.domain.booking.model.BookingResponseDTO;
import com.goldenhour.domain.booking.model.BookingUpdateDTO;
import com.goldenhour.domain.booking.repository.BookingRepository;
import com.goldenhour.domain.hotel.entity.Hotel;
import com.goldenhour.domain.hotel.service.HotelService;
import com.goldenhour.domain.travel.service.TravelService;
import com.goldenhour.domain.user.service.UserService;
import com.goldenhour.infrastructure.handler.exceptions.ConflictException;
import com.goldenhour.infrastructure.handler.exceptions.ForbiddenException;
import com.goldenhour.infrastructure.handler.exceptions.NotFoundException;
import com.goldenhour.infrastructure.mapper.BookingMapper;
import com.goldenhour.infrastructure.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private static final String BOOKING_NOT_EXISTS = "Booking with id %d doesn't exist";

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final UserService userService;
    private final TravelService travelService;
    private final HotelService hotelService;
    private final AuthenticationService authenticationService;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository,
                              BookingMapper bookingMapper,
                              UserService userService,
                              TravelService travelService,
                              HotelService hotelService,
                              AuthenticationService authenticationService) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
        this.userService = userService;
        this.travelService = travelService;
        this.hotelService = hotelService;
        this.authenticationService = authenticationService;
    }

    @Override
    public BookingResponseDTO createBooking(BookingRequestDTO bookingDTO) {
        Booking booking = bookingMapper.toBooking(bookingDTO);
        var user = userService.getById(bookingDTO.userId());
        var travel = travelService.getById(bookingDTO.travelId());
        var hotel = hotelService.getById(bookingDTO.hotelId());

        var totalPrice = calculateTotalPrice(travel.getNumberOfNights(), hotel.getPricePerNight(), bookingDTO.numberOfPeople(), bookingDTO.ownTransport());

        booking.setTotalPrice(totalPrice);
        booking.setUser(user);
        booking.setTravel(travel);
        booking.setHotel(hotel);

        checkHotelExistsInDestination(bookingDTO.hotelId(), bookingDTO.travelId());
        checkRoomAvailability(hotel);
        validateExistsByUserAndTravel(bookingDTO.userId(), bookingDTO.travelId());

        bookingRepository.save(booking);

        hotelService.updateNumberOfAvailableRooms(hotel);

        return bookingMapper.toBookingResponseDTO(booking);
    }

    private Double calculateTotalPrice(int numberOfNights, double pricePerNight, int numberOfPeople, boolean ownTransport) {
        var totalPrice = numberOfNights * pricePerNight * numberOfPeople;

        if (!ownTransport) {
            totalPrice = totalPrice * 1.15;
        }

        return totalPrice;
    }

    private void checkHotelExistsInDestination(Long hotelId, Long travelId) {
        if (!bookingRepository.checkHotelExistsInDestination(hotelId, travelId)) {
            throw new NotFoundException("Hotel with id %d doesn't exist in the planned trip that has id %d".formatted(hotelId, travelId));
        }
    }

    private void checkRoomAvailability(Hotel hotel) {
        if (hotel.getAvailableRooms() == 0) {
            throw new ForbiddenException("Hotel %s currently has 0 available rooms".formatted(hotel.getName()));
        }
    }

    private void validateExistsByUserAndTravel(Long userId, Long travelId) {
        if (bookingRepository.existsByUser_IdAndTravel_Id(userId, travelId)) {
            throw new ConflictException("User with id %d has already booked a trip with id %d".formatted(userId, travelId));
        }
    }

    @Override
    public Page<BookingResponseDTO> getAllBookings(Pageable pageable) {
        return bookingRepository.findAll(pageable).map(bookingMapper::toBookingResponseDTO);
    }

    @Override
    public BookingResponseDTO getBookingById(Long id) {
        return bookingMapper.toBookingResponseDTO(getById(id));
    }

    private Booking getById(Long id) {
        Optional<Booking> optionalBooking = bookingRepository.findById(id);

        if (optionalBooking.isEmpty()) {
            throw new NotFoundException(BOOKING_NOT_EXISTS.formatted(id));
        }

        return optionalBooking.get();
    }

    @Override
    public List<BookingResponseDTO> getBookingsByUser(Long userId) {
        List<Booking> bookingsByUser = bookingRepository.findBookingsByUser(userId);
        var user = userService.getById(userId);
        userService.existsById(userId);

        authenticationService.canUserAccess(user.getUsername(), "You don't have permission to view other user's bookings");

        if (bookingsByUser.isEmpty()) {
            throw new NotFoundException("There are 0 bookings for user with id %d".formatted(userId));
        }

        return getBookingListResponseDTO(bookingsByUser);
    }

    @Override
    public List<BookingResponseDTO> getBookingsByTravel(Long travelId) {
        List<Booking> bookingsByTravel = bookingRepository.findBookingsByTravel(travelId);
        travelService.existsById(travelId);

        if (bookingsByTravel.isEmpty()) {
            throw new NotFoundException("List of bookings by travel with id %d is empty".formatted(travelId));
        }

        return getBookingListResponseDTO(bookingsByTravel);
    }

    @Override
    public List<BookingResponseDTO> getBookingsByHotel(Long hotelId) {
        List<Booking> bookingsByHotel = bookingRepository.findBookingsByHotel(hotelId);
        hotelService.existsById(hotelId);

        if (bookingsByHotel.isEmpty()) {
            throw new NotFoundException("List of bookings by hotel that has id %d is empty".formatted(hotelId));
        }

        return getBookingListResponseDTO(bookingsByHotel);
    }

    private List<BookingResponseDTO> getBookingListResponseDTO(List<Booking> bookingList) {
        return bookingList
                .stream()
                .map(bookingMapper::toBookingResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponseDTO updateBooking(Long id, BookingUpdateDTO bookingDTO) {
        Booking booking = getById(id);
        bookingMapper.updateBookingFromDTO(bookingDTO, booking);

        bookingRepository.save(booking);

        return bookingMapper.toBookingResponseDTO(booking);
    }

    @Override
    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new NotFoundException(BOOKING_NOT_EXISTS.formatted(id));
        }

        bookingRepository.deleteById(id);
    }

}
