package com.example.demo.Models.DTO;

import com.example.demo.Models.SeatStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatAvailabilityDTO {
    private Long id;
    private Long showId;
    private Long seatId;
    private Long userId;
    private Long bookingId;
    private SeatStatusEnum status;
    private
}
