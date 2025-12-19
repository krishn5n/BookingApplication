package com.example.demo.Tables;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public VenueEntity(String name, String location, String city, Double latitude, Double longitude){
        this.name = name;
        this.location = location;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}