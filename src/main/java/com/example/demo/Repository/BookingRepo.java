package com.example.demo.Repository;

import com.example.demo.Tables.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepo extends JpaRepository<BookingEntity, Long> {

    @Query("Select b from bookings b JOIN FETCH b.show s JOIN FETCH s.screenEntity sc JOIN FETCH s.eventEntity e JOIN FETCH sc.venue v JOIN FETCH b.user u WHERE u.id = :id")
    public List<BookingEntity> findBookingsByUser(@Param("id") Long id);
}
