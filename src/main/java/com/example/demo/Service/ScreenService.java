package com.example.demo.Service;

import com.example.demo.Models.DTO.ScreenDTO;
import com.example.demo.Repository.ScreenRepo;
import com.example.demo.Repository.VenueRepo;
import com.example.demo.Tables.ScreenEntity;
import com.example.demo.Tables.VenueEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ScreenService {
    private final ScreenRepo screenRepo;
    private final VenueRepo venueRepo;


    public ScreenService(ScreenRepo screenRepo, VenueRepo venueRepo){
        this.screenRepo = screenRepo;
        this.venueRepo = venueRepo;
    }

    public List<ScreenDTO> getScreen() {
        List<ScreenEntity> screenEntities = screenRepo.findAll();
        List<ScreenDTO> screenDTOS = new ArrayList<>();
        for(ScreenEntity screenEntity: screenEntities){
            screenDTOS.add(screenEntity.convertToDTO());
        }
        return screenDTOS;
    }

    public void addScreen(ScreenDTO addDetails) {
        System.out.println("Screen DTO is "+addDetails);
        long venueId = addDetails.getVenueId();
        VenueEntity venueEntity = venueRepo.getReferenceById(venueId);
        ScreenEntity screenEntity = new ScreenEntity(venueEntity, addDetails.getName());
        System.out.println("Screen Entity is "+screenEntity);
        screenRepo.save(screenEntity);
    }

    public void updateScreen(Map<String, String> modifyDetails) {
        return;
    }

    public void deleteVenue(ScreenDTO screenDTO) {
        screenRepo.findById(screenDTO.getId()).ifPresent(screenRepo::delete);
    }

    public void deleteScreen(ScreenDTO deleteDetails) {
        return;
    }
}
