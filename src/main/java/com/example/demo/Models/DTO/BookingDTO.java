package com.example.demo.Models.DTO;

import com.example.demo.Tables.BookingEntity;
import com.example.demo.Tables.ScreenEntity;
import com.example.demo.Tables.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    private Long id;
    private Long screenEntityId;
    private Long userId;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt = LocalDateTime.now();

    public BookingEntity convertToEntity(){
        return new BookingEntity();
    }
}
