package com.example.demo.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowDTO {
    private Long id;
    private LocalDate showDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long screenId;
    private Long eventId;

    public ShowDTO(LocalDate showDate, LocalTime startTime, LocalTime endTime, Long screenId, Long eventId){
        this.showDate = showDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.screenId = screenId;
        this.eventId = eventId;
    }
}
