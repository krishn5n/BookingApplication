package com.example.demo.Service;

import com.example.demo.Models.DTO.*;
import com.example.demo.Repository.EventRepo;
import com.example.demo.Repository.ScreenRepo;
import com.example.demo.Repository.ShowRepo;
import com.example.demo.Tables.EventEntity;
import com.example.demo.Tables.ScreenEntity;
import com.example.demo.Tables.ShowEntity;
import com.example.demo.Tables.VenueEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class ShowService {
    private final ShowRepo showRepo;
    private final EventRepo eventRepo;
    private final ScreenRepo screenRepo;

    public ShowService(ShowRepo showRepo, EventRepo eventRepo, ScreenRepo screenRepo){
        this.showRepo = showRepo;
        this.eventRepo = eventRepo;
        this.screenRepo = screenRepo;
    }

    public List<ShowDTO> getShows() {
        List<ShowEntity> showEntities = showRepo.findAll();
        List<ShowDTO> showDTOS = new ArrayList<>();
        for(ShowEntity showEntity: showEntities){
            showDTOS.add(showEntity.convertToDTO());
        }
        return showDTOS;
    }

    public void addShow(ShowDTO addDetails) {
        System.out.println("ShowDTO DTO is "+addDetails);
        long eventId = addDetails.getEventId();
        long screenId = addDetails.getScreenId();
        EventEntity eventEntity = eventRepo.getReferenceById(eventId);
        ScreenEntity screenEntity = screenRepo.getReferenceById(screenId);
        ShowEntity showEntity = new ShowEntity(addDetails.getShowDate(),addDetails.getStartTime(),addDetails.getEndTime(),screenEntity,eventEntity);
        System.out.println("Show Entity is "+showEntity);
        showRepo.save(showEntity);
    }

    public void deleteShow(ShowDTO deleteDetails) {
        showRepo.findById(deleteDetails.getId()).ifPresent(showRepo::delete);
    }

    public void updateShow(Map<String, String> modifyDetails) {
        Optional<ShowEntity> potentialRowValue =  showRepo.findById(Long.valueOf(modifyDetails.get("id")));
        if (potentialRowValue.isEmpty()) {
            return; // Or throw an exception
        }

        ShowEntity rowValue = potentialRowValue.get();
        modifyDetails.forEach((key,value)->{
            switch (key){
                case "showDate":{
                    rowValue.setShowDate(LocalDate.parse(value));
                    break;
                }
                case "startTime" : {
                    rowValue.setStartTime(LocalTime.parse(value));
                    break;
                }
                case "endTime" : {
                    rowValue.setEndTime(LocalTime.parse((value)));
                    break;
                }
                case "screenId" : {
                    ScreenEntity screenEntity = screenRepo.getReferenceById(Long.valueOf(value));
                    rowValue.setScreenEntity(screenEntity);
                    break;
                }
                case "eventId" : {
                    EventEntity eventEntity = eventRepo.getReferenceById(Long.valueOf(value));
                    rowValue.setEventEntity(eventEntity);
                    break;
                }
            }
        });
        showRepo.save(rowValue);
    }

    //Getters
    public List<AllDetailsDTO> getShowsByEvent(Long eventId){
        List<ShowEntity> showEntities = showRepo.findAllDetailsUsingEventId(eventId);
        List<AllDetailsDTO> returnValue = new ArrayList<>();

        for(ShowEntity showEntity: showEntities){
            EventDTO eventDTO = showEntity.getEventEntity().convertToDTO();
            ScreenDTO screenDTO = showEntity.getScreenEntity().convertToDTO();
            VenueDTO venueDTO = showEntity.getScreenEntity().getVenue().convertToDTO();
            ShowDTO showDTO = showEntity.convertToDTO();
            LocationDetailsDTO locationDetailsDTO = new LocationDetailsDTO(venueDTO,screenDTO);
            AllDetailsDTO allDetailsDTO = new AllDetailsDTO(locationDetailsDTO,eventDTO,showDTO);
            returnValue.add(allDetailsDTO);
        }
        return returnValue;
    }
}
