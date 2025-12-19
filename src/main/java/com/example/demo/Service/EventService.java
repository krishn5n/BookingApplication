package com.example.demo.Service;

import com.example.demo.Models.EventDTO;
import com.example.demo.Repository.EventRepo;
import com.example.demo.Tables.EventEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service @Transactional
public class EventService {
    private final EventRepo eventRepo;

    public EventService(EventRepo eventRepo){
        this.eventRepo = eventRepo;
    }

    public void addEvent(EventDTO eventDetails) {
        EventEntity eventEntity = eventDetails.convertToEventEntity();
        eventRepo.save(eventEntity);
    }

    public List<EventEntity> getEvents() {
        return eventRepo.findAll();
    }

    public void updateEvent(Map<String,String> updateDetails) {
        Optional<EventEntity> potentialRowValue =  eventRepo.findById(Long.valueOf(updateDetails.get("id")));
        if (potentialRowValue.isEmpty()) {
            return; // Or throw an exception
        }

        EventEntity rowValue = potentialRowValue.get();
        updateDetails.forEach((key,value)->{
            switch (key){
                case "name":{
                    rowValue.setName(value);
                    break;
                }
                case "genre" : {
                    rowValue.setGenre(value);
                    break;
                }
                case "language" : {
                    rowValue.setLanguage(value);
                    break;
                }
                case "desc" : {
                    rowValue.setDesc(value);
                    break;
                }
            }
        });
    }

    public void deleteEvent(EventDTO eventDetails) {
        eventRepo.findById(eventDetails.getId()).ifPresent(event -> eventRepo.delete(event));
    }
}
