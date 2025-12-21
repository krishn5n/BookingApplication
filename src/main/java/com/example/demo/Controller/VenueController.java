package com.example.demo.Controller;

import com.example.demo.Models.DTO.ScreenDTO;
import com.example.demo.Models.DTO.VenueDTO;
import com.example.demo.Service.VenueService;
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
    public ResponseEntity<List<VenueDTO>> getVenues(){
        try {
            List<VenueDTO> retrunList = venueService.getVenues();
            return new ResponseEntity<>(retrunList, HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            List<VenueDTO> returnList = new ArrayList<>();
            return new ResponseEntity<List<VenueDTO>>(returnList,HttpStatusCode.valueOf(500));
        }
    }

    @PreAuthorize("hasAuthority('A')")
    @PostMapping("/add")
    public ResponseEntity<String> addVenue(@RequestBody VenueDTO venueDetails){
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
    public ResponseEntity<String> modifyVenue(@RequestBody Map<String,String> venueDetails){
        try{
            venueService.updateVenue(venueDetails);
            return new ResponseEntity<>("success",HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            String retval = "Error at Modifying Event " + e.getMessage();
            return new ResponseEntity<>(retval,HttpStatusCode.valueOf(500));
        }
    }

    @PreAuthorize("hasAuthority('A')")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteVenue(@RequestBody VenueDTO venueDTO){
        try {
            venueService.deleteVenue(venueDTO);
            return new ResponseEntity<>("Successful",HttpStatusCode.valueOf(200));
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatusCode.valueOf(500));
        }
    }


    @PreAuthorize("hasAnyAuthority('U','A')")
    @GetMapping("/get/search")
    public ResponseEntity<List<VenueDTO>> getVenueByName(@RequestParam Map<String,String> searchDetails) {
        try {
            List<VenueDTO> retrunList = venueService.getVenuesByName(searchDetails);
            return new ResponseEntity<>(retrunList, HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            List<VenueDTO> returnList = new ArrayList<>();
            return new ResponseEntity<List<VenueDTO>>(returnList,HttpStatusCode.valueOf(500));
        }
    }

    @PreAuthorize("hasAnyAuthority('U','A')")
    @GetMapping("/get/screen/{venueId}")
    public ResponseEntity<List<ScreenDTO>> getScreenByVenue(@PathVariable Long venueId) {
        try{
            List<ScreenDTO> returnValue = venueService.getScreenByVenueId(venueId);
            return new ResponseEntity<List<ScreenDTO>>(returnValue,HttpStatusCode.valueOf(200));
        }
        catch(Exception e){
            List<ScreenDTO> arr = new ArrayList<>();
            System.out.println(e.getMessage());
            return new ResponseEntity<List<ScreenDTO>>(arr,HttpStatusCode.valueOf(500));
        }
    }
}