package com.example.demo.Models.DTO;

import com.example.demo.Tables.ScreenEntity;
import com.example.demo.Tables.SeatEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.internal.build.AllowNonPortable;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatDTO {
    private Long id;
    private Long screenId;
    private int rowNumber;
    private int colNumber;
    private String seatName;
    private BigDecimal seatPrice;
}
