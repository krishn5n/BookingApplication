package com.example.demo.Models.DTO;

import com.example.demo.Tables.EventEntity;
import com.example.demo.Tables.ShowEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllDetailsDTO {
    private LocationDetailsDTO locationDetailsDTO;
    private EventDTO eventDTO;
    private ShowDTO showDTO;
    private BookingDTO bookingDTO;

    public AllDetailsDTO(LocationDetailsDTO locationDetailsDTO, EventDTO eventDTO, ShowDTO showDTO){
        this.locationDetailsDTO = locationDetailsDTO;
        this.eventDTO = eventDTO;
        this.showDTO = showDTO;
    }
}
