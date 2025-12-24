package com.example.demo.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


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

    public String mailBody(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        return "\nShow Date "+showDate.toString()
                +"\nShow Start Time "+startTime.format(formatter)
                +"\n";
    }
}
