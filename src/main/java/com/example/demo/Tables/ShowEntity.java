package com.example.demo.Tables;

import com.example.demo.Models.DTO.ShowDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity(name="Shows")
@Table(name="Shows")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "show_date", nullable = false)
    private LocalDate showDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="screen_id", nullable = false)
    @JsonBackReference
    private ScreenEntity screenEntity;


    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="event_id", nullable = false)
    @JsonBackReference
    private EventEntity eventEntity;

    public ShowEntity(LocalDate showDate, LocalTime startTime, LocalTime endTime, ScreenEntity screenEntity, EventEntity eventEntity){
        this.showDate = showDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.screenEntity = screenEntity;
        this.eventEntity = eventEntity;
    }

    @Override
    public String toString() {
        return "ShowEntity{" +
                "endTime=" + endTime +
                ", startTime=" + startTime +
                ", showDate=" + showDate +
                ", id=" + id +
                '}';
    }

    public ShowDTO convertToDTO(){
        return new ShowDTO(this.id,this.showDate,this.startTime,this.endTime,this.screenEntity.getId(),this.eventEntity.getId());
    }
}
