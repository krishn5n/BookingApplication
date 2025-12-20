package com.example.demo.Tables;

import com.example.demo.Models.DTO.EventDTO;
import com.example.demo.Models.EventRatingEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="events")
@Table(name = "events")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "genre")
    private String genre;

    @Column(nullable = false, name = "language")
    private String language;

    @Column(nullable = false, name = "rating", columnDefinition = "event_rating")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private EventRatingEnum rating;

    @Column(nullable = false, name = "descr")
    private String desc;

    @Column(nullable = false, name = "duration")
    private Double duration;

    public EventEntity(String name, String genre, String desc, String language, EventRatingEnum rating, Double duration) {
        this.name = name;
        this.genre = genre;
        this.desc = desc;
        this.language = language;
        this.rating = rating;
        this.duration = duration;
    }

    public EventDTO convertToDTO(){
       return new EventDTO(id,name,genre,language,rating,desc,duration);
    }
}