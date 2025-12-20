package com.example.demo.Tables;
import com.example.demo.Models.DTO.VenueDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "venues")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VenueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String city;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(nullable = false)
    private String location;

    @OneToMany(mappedBy = "venueId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ScreenEntity> screens = new ArrayList<>();

    public VenueEntity(String name, String location, String city, Double latitude, Double longitude){
        this.name = name;
        this.location = location;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "VenueEntity{" +
                "city='" + city + '\'' +
                ", id=" + id +
                ", latitude=" + latitude +
                ", location='" + location + '\'' +
                ", longitude=" + longitude +
                ", name='" + name + '\'' +
                '}';
    }

    public VenueDTO convertToDTO(){
        return new VenueDTO(id,name,city,latitude,longitude,location);
    }
}