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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    private Long id;
    private Long screenEntityId;
    private List<SeatDTO> seats;
    private Long userId;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt = LocalDateTime.now();

    public BookingDTO(BigDecimal totalAmount, LocalDateTime createdAt){
        this.totalAmount = totalAmount;
        this.createdAt =createdAt;
    }

    public BookingEntity convertToEntity(){
        return new BookingEntity();
    }

    public String mailBody(){
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss");
        List<String> seatid = new ArrayList<>();
        for(SeatDTO seatDTO:seats){
            seatid.add(seatDTO.getSeatName());
        }
        return "\nSeats Booked - " + String.join(",",seatid)
                +"\n Created at - "+createdAt.format(customFormatter)
                +"\nTotal Amount - "+totalAmount.toPlainString()
                +"\n";
    }
}
