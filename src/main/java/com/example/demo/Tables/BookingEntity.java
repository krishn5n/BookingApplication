package com.example.demo.Tables;

import com.example.demo.Models.DTO.BookingDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "bookings")
@Table(name = "bookings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "show_id")
    @JsonBackReference
    private ShowEntity show;

    @Column(name = "total_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(fetch = FetchType.LAZY)
    private List<SeatAllocationEntity> seatAvailabilityEntityList;

    public BookingEntity(User user, ShowEntity show, BigDecimal totalAmount, LocalDateTime createdAt){
        this.user = user;
        this.show = show;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
    }

}
