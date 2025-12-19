package com.example.demo.Controller;

import com.example.demo.Models.VenueDTO;
import com.example.demo.Security.VenueService;
import com.example.demo.Tables.VenueEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/venue")
public class VenueController {
    private final VenueService venueService;

    public VenueController(VenueService venueService){
        this.venueService = venueService;
    }

    @PreAuthorize("hasAnyAuthority('U','A')")
    @GetMapping("/get")
    public ResponseEntity<List<VenueEntity>> getVenues(){
        try {
            List<VenueEntity> retrunList = venueService.getVenues();
            return new ResponseEntity<>(retrunList, HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            List<VenueEntity> returnList = new ArrayList<>();
            return new ResponseEntity<List<VenueEntity>>(returnList,HttpStatusCode.valueOf(500));
        }
    }


    @PreAuthorize("hasAuthority('A')")
    @PostMapping("/add")
    public ResponseEntity<String> addEvent(@RequestBody VenueDTO venueDetails){
        try {
            venueService.addVenue(venueDetails);
            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            String retval = "Error at Adding Event " + e.getMessage();
            return new ResponseEntity<>(retval,HttpStatusCode.valueOf(500));
        }
    }

    @PreAuthorize("hasAuthority('A')")
    @PostMapping("/modify")
    public ResponseEntity<Boolean> modifyEvent(@RequestBody Map<String,String> venueDetails){
        try{
            venueService.updateVenue(venueDetails);
            return new ResponseEntity<>(true,HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(false,HttpStatusCode.valueOf(500));
        }
    }

    @PreAuthorize("hasAuthority('A')")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteEvent(@RequestBody VenueDTO venueDTO){
        try {
            venueService.deleteEvent(venueDTO);
            return new ResponseEntity<>("Successful",HttpStatusCode.valueOf(200));
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatusCode.valueOf(500));
        }
    }

}