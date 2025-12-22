package com.example.demo.Tables;

import com.example.demo.Models.DTO.SeatAvailabilityDTO;
import com.example.demo.Models.SeatStatusEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.awt.print.Book;
import java.time.LocalDateTime;

@Entity(name = "seatAvailability")
@Table(name = "seat_availability", uniqueConstraints = {@UniqueConstraint(columnNames = {"seat_id","show_id"})})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatAvailabilityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "status", length = 20)
    private SeatStatusEnum status;

    @Column(name = "lock_expiry",nullable = false)
    private LocalDateTime lockExpiry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false)
    @JsonBackReference
    private SeatEntity seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id", nullable = false)
    @JsonBackReference
    private ShowEntity show;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locked_by_used_id", nullable = false)
    @JsonBackReference
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    @JsonBackReference
    private BookingEntity booking;

    public SeatAvailabilityEntity(SeatEntity seat, SeatStatusEnum seatStatus, LocalDateTime lockExpiry, ShowEntity show, User user, BookingEntity booking){
        this.seat = seat;
        this.status = seatStatus;
        this.lockExpiry = lockExpiry;
        this.show = show;
        this.user = user;
        this.booking = booking;
    }

    public SeatAvailabilityDTO convertToDTO(){
        return new SeatAvailabilityDTO(id,show.getId(),seat.getId(),status,)
    }

}
