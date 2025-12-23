package com.example.demo.Service;

import com.example.demo.Models.DTO.*;
import com.example.demo.Repository.EventRepo;
import com.example.demo.Repository.ScreenRepo;
import com.example.demo.Repository.ShowRepo;
import com.example.demo.Tables.*;
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
    private final SeatAllocationService seatAllocationService;

    public ShowService(ShowRepo showRepo, EventRepo eventRepo, ScreenRepo screenRepo, SeatAllocationService seatAllocationService){
        this.showRepo = showRepo;
        this.eventRepo = eventRepo;
        this.screenRepo = screenRepo;
        this.seatAllocationService = seatAllocationService;
    }

    public List<ShowDTO> getShows() {
        List<ShowEntity> showEntities = showRepo.findAll();
        List<ShowDTO> showDTOS = new ArrayList<>();
        for(ShowEntity showEntity: showEntities){
            showDTOS.add(showEntity.convertToDTO());
        }
        return showDTOS;
    }

    public void addShow(List<ShowDTO> addDetails, Long userId) {
        System.out.println("ShowDTO DTO is "+addDetails);
        List<ShowEntity> showEntities = new ArrayList<>();
        for(ShowDTO showDTO:addDetails){
            long eventId = showDTO.getEventId();
            long screenId = showDTO.getScreenId();
            EventEntity eventEntity = eventRepo.getReferenceById(eventId);
            ScreenEntity screenEntity = screenRepo.getReferenceById(screenId);
            ShowEntity showEntity = new ShowEntity(showDTO.getShowDate(),showDTO.getStartTime(),showDTO.getEndTime(),screenEntity,eventEntity);
            showEntities.add(showEntity);
        }
        List<ShowEntity> savedShows = showRepo.saveAll(showEntities);
        seatAllocationService.createSeat(addDetails,savedShows, userId);
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
