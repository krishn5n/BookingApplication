package com.example.demo.Models.DTO;

import com.example.demo.Tables.VenueEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VenueDTO {
    private Long id;
    private String name;
    private String city;
    private Double latitude;
    private Double longitude;
    private String location;

    public VenueEntity convertToVenueEntity(){
        return new VenueEntity(name,location,city,latitude,longitude);
    }

    public String mailBody(){
        return "\nVenue name - "+name
                +"\n City - "+city
                +"\n Location - "+location
                +"\n";
    }
}
