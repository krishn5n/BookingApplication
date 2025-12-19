package com.example.demo.Security;

import com.example.demo.Models.VenueDTO;
import com.example.demo.Repository.VenueRepo;
import com.example.demo.Tables.VenueEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VenueService {
    private final VenueRepo venueRepo;
    public VenueService(VenueRepo venueRepo){
        this.venueRepo = venueRepo;
    }

    public List<VenueEntity> getVenues() {
        return venueRepo.findAll();
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

    public void deleteEvent(VenueDTO venueDTO) {
        venueRepo.findById(venueDTO.getId()).ifPresent(venueRepo::delete);
    }
}
