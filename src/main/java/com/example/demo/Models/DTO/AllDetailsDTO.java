package com.example.demo.Models.DTO;

import com.example.demo.Tables.EventEntity;
import com.example.demo.Tables.ShowEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AllDetailsDTO {
    private LocationDetailsDTO screenEntity;
    private EventDTO eventEntity;
    private ShowDTO showEntity;
}
