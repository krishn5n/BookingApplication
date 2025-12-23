package com.example.demo.Tables;

import com.example.demo.Models.SeatStatusEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity(name = "seatAvailability")
@Table(name = "seat_availability", uniqueConstraints = {@UniqueConstraint(columnNames = {"seat_id","show_id"})})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatAllocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private SeatStatusEnum status;

    @Column(name = "lock_expiry")
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
    @JoinColumn(name = "locked_by_user_id", nullable = true)
    @JsonBackReference
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    @JsonBackReference
    private BookingEntity booking;

    public SeatAllocationEntity(SeatEntity seat, SeatStatusEnum seatStatus, ShowEntity show){
        this.seat = seat;
        this.status = seatStatus;
        this.show = show;
    }


    public SeatAllocationEntity(SeatEntity seat, SeatStatusEnum seatStatus,LocalDateTime lockExpiry,ShowEntity show, User user){
        this.seat = seat;
        this.status = seatStatus;
        this.show = show;
        this.user = user;
        this.lockExpiry = lockExpiry;
    }

}
