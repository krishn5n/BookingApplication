package com.example.demo.Repository;

import com.example.demo.Models.SeatStatusEnum;
import com.example.demo.Tables.SeatAllocationEntity;
import jakarta.persistence.LockModeType;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface SeatAllocationRepo extends JpaRepository<SeatAllocationEntity,Long> {
    //For actualling taking Lock for entire transaction
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM seatAvailability s WHERE s.show.id = :showId AND s.seat.id IN :seatIds AND s.status = 'available'")
    List<SeatAllocationEntity> findAllByIdWithLock(Long showId, List<Long> seatIds);

    @Modifying
    @Transactional
    @Query("Update seatAvailability s SET s.status = 'available', s.user = null, s.lockExpiry =null WHERE s.lockExpiry<:now AND s.status = 'pending'")
    int releaseLockedSeats(@Param("now") LocalDateTime now);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM seatAvailability s " +
            "JOIN FETCH s.seat se " +
            "JOIN FETCH se.screen sc " +
            "JOIN FETCH sc.venue v " +
            "JOIN FETCH s.show sh " +
            "JOIN FETCH sh.eventEntity e "+
            "WHERE s.show.id = :showId " +
            "AND se.id IN :seatIds " +
            "AND s.user.id = :userId " +
            "AND s.status = :status " +
            "AND s.lockExpiry > :dateTime")
    List<SeatAllocationEntity> findLockedSeatsByUser(
            @Param("showId") Long showId,
            @Param("seatIds") List<Long> seatIds,
            @Param("userId") Long userId,
            @Param("status") SeatStatusEnum status,
            @Param("dateTime") LocalDateTime dateTime
    );
}
