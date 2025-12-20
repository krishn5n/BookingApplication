package com.example.demo.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScreenDTO {
    private Long id;
    private Long venueId;
    private String name;

    public ScreenDTO(Long id, String name, Long venueId){
        this.id = id;
        this.name = name;
        this.venueId = venueId;
    }
}
