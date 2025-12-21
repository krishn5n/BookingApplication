package com.example.demo.Service;

import com.example.demo.Models.DTO.ScreenDTO;
import com.example.demo.Models.DTO.VenueDTO;
import com.example.demo.Repository.ScreenRepo;
import com.example.demo.Repository.VenueRepo;
import com.example.demo.Tables.ScreenEntity;
import com.example.demo.Tables.VenueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class VenueService {
    private final VenueRepo venueRepo;
    public final ScreenRepo screenRepo;
    public VenueService(VenueRepo venueRepo, ScreenRepo screenRepo){
        this.venueRepo = venueRepo;
        this.screenRepo = screenRepo;
    }

    public List<VenueDTO> getVenues() {
        List<VenueDTO> returnValue = new ArrayList<>();
        List<VenueEntity> venueEntity = venueRepo.findAll();
        for(VenueEntity itrvenueEntity: venueEntity){
            returnValue.add(itrvenueEntity.convertToDTO());
        }
        return returnValue;
    }

    public void addVenue(VenueDTO venueDetails) {
        VenueEntity venueEntity = venueDetails.convertToVenueEntity();
        venueRepo.save(venueEntity);
    }

    public void updateVenue(Map<String, String> venueDetails) {
        Optional<VenueEntity> potentialRowValue =  venueRepo.findById(Long.valueOf(venueDetails.get("id")));
        if (potentialRowValue.isEmpty()) {
            return; // Or throw an exception
        }

        VenueEntity rowValue = potentialRowValue.get();
        venueDetails.forEach((key,value)->{
            switch (key){
                case "name":{
                    rowValue.setName(value);
                    break;
                }
                case "city" : {
                    rowValue.setCity(value);
                    break;
                }
                case "location" : {
                    rowValue.setLocation(value);
                    break;
                }
                case "latitude" : {
                    rowValue.setLatitude(Double.valueOf(value));
                    break;
                }
                case "longitude" : {
                    rowValue.setLongitude(Double.valueOf(value));
                    break;
                }
            }
        });
        venueRepo.save(rowValue);
    }

    public void deleteVenue(VenueDTO venueDTO) {
        venueRepo.findById(venueDTO.getId()).ifPresent(venueRepo::delete);
    }

    @Transactional
    public List<VenueDTO> getVenuesByName(Map<String,String> filterVariables) {
        List<VenueEntity> venueEntities =  venueRepo.findByNameContaining(filterVariables.get("name"));
        List<VenueDTO> venueDTOS = new ArrayList<>();
        for(VenueEntity venueEntity: venueEntities){
            venueDTOS.add(venueEntity.convertToDTO());
        }
        return venueDTOS;
    }

    public List<ScreenDTO> getScreenByVenueId(Long venueId) {
        List<ScreenEntity> screenEntities = screenRepo.findByVenue_Id(venueId);
        List<ScreenDTO> screenDTOS = new ArrayList<>();
        for(ScreenEntity screenEntity: screenEntities){
            screenDTOS.add(screenEntity.convertToDTO());
        }
        return screenDTOS;
    }
}
