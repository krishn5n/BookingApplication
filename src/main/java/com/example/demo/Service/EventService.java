package com.example.demo.Service;

import com.example.demo.Models.DTO.EventDTO;
import com.example.demo.Repository.EventRepo;
import com.example.demo.Service.Tools.RedisService;
import com.example.demo.Tables.EventEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service @Transactional
public class EventService {
    private final EventRepo eventRepo;
    private final RedisService redisService;

    public EventService(EventRepo eventRepo, RedisService redisService){
        this.eventRepo = eventRepo;
        this.redisService = redisService;
    }

    public void addEvent(EventDTO eventDetails) {
        EventEntity eventEntity = eventDetails.convertToEventEntity();
        eventRepo.save(eventEntity);
    }

    public List<EventDTO> getEvents() {
        String keyToSearch = "get_events";
        List<EventDTO> eventList = redisService.get(keyToSearch,new TypeReference<List<EventDTO>>(){});
        if(eventList != null){
            return eventList;
        }
        List<EventEntity> eventEntities = eventRepo.findAll();
        List<EventDTO> returnList = new ArrayList<>();
        for(EventEntity eventEntity: eventEntities){
            returnList.add(eventEntity.convertToDTO());
        }
        redisService.set(keyToSearch,returnList,5L);
        return returnList;
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
