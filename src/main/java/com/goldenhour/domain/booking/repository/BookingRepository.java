package com.goldenhour.domain.booking.repository;

import com.goldenhour.domain.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByUser_IdAndTravel_Id(Long userId, Long travelId);

    @Query("SELECT COUNT(h) > 0 FROM hotel h " +
            "JOIN destination d ON h.destination.id = d.id " +
            "JOIN travel t ON d.id = t.destination.id " +
            "WHERE h.id = :hotelId AND t.id = :travelId")
    boolean checkHotelExistsInDestination(Long hotelId, Long travelId);

    @Query("SELECT b FROM booking b " +
            "WHERE b.user.id = :userId")
    List<Booking> findBookingsByUser(Long userId);

    @Query("SELECT b FROM booking b " +
            "WHERE b.travel.id = :travelId")
    List<Booking> findBookingsByTravel(Long travelId);

    @Query("SELECT b FROM booking b " +
            "WHERE b.hotel.id = :hotelId")
    List<Booking> findBookingsByHotel(Long hotelId);

}
