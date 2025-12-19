package com.example.demo.Models;

import com.example.demo.Tables.EventEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    private Long id;
    private String name;
    private String genre;
    private String language;
    private EventRatingEnum rating;
    private String desc;

    public EventEntity convertToEventEntity(){
        return new EventEntity(this.name, this.genre, this.desc, this.language, this.rating);
    }
}
