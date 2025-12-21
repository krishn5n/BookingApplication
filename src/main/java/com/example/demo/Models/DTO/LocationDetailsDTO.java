package com.example.demo.Models.DTO;

import com.example.demo.Tables.ScreenEntity;
import com.example.demo.Tables.VenueEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationDetailsDTO {
    private VenueDTO venueEntity;
    private ScreenDTO screenEntity;
}
