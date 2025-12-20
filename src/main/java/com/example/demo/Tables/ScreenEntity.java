package com.example.demo.Tables;

import com.example.demo.Models.DTO.ScreenDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Screens")
@Table(name = "Screens")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScreenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "venue_id", nullable = false)
    @JsonBackReference
    private VenueEntity venueId;

    @Column(nullable = false, name = "name")
    private String name;

    public ScreenEntity(VenueEntity venueId, String name){
        this.venueId = venueId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "ScreenEntity{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    public ScreenDTO convertToDTO(){
        return new ScreenDTO(id,name,venueId.getId());
    }
}
