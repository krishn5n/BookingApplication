package com.example.demo.Models.DTO;

import com.example.demo.Models.EventRatingEnum;
import com.example.demo.Tables.EventEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Double duration;

    public EventEntity convertToEventEntity(){
        return new EventEntity(this.name, this.genre, this.desc, this.language, this.rating,this.duration);
    }
}
