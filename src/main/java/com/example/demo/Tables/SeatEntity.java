package com.example.demo.Tables;

import com.example.demo.Models.DTO.SeatDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity(name="seats")
@Table(name = "seats",uniqueConstraints = {@UniqueConstraint(columnNames = {"screen_id","row_number","col_number"})})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "row_number",nullable = false)
    private int rowNumber;

    @Column(name = "col_number",nullable = false)
    private int colNumber;

    @Column(name = "seat_name",length = 10,nullable = false)
    private String seatName;

    @Column(name="seat_price",nullable = false,precision = 10,scale = 2)
    private BigDecimal seatPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id", nullable = false)
    private ScreenEntity screen;

    public SeatEntity(int rowNumber, int colNumber, String seatName, BigDecimal seatPrice, ScreenEntity screen){
        this.rowNumber = rowNumber;
        this.colNumber = colNumber;
        this.seatName = seatName;
        this.seatPrice = seatPrice;
        this.screen = screen;
    }

    public SeatDTO convertToDTO(){
        return new SeatDTO(id,screen.getId(),rowNumber,colNumber,seatName,seatPrice);
    }

    @Override
    public String toString() {
        return "SeatEntity{" +
                "seatPrice=" + seatPrice +
                ", seatName='" + seatName + '\'' +
                ", colNumber=" + colNumber +
                ", rowNumber=" + rowNumber +
                ", id=" + id +
                '}';
    }
}
