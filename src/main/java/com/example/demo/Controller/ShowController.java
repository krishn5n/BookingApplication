package com.example.demo.Controller;

import com.example.demo.Models.DTO.AllDetailsDTO;
import com.example.demo.Models.DTO.ShowDTO;
import com.example.demo.Service.ShowService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/show")
public class ShowController {
    private final ShowService showService;

    public ShowController(ShowService showService){
        this.showService = showService;
    }

    @PreAuthorize("hasAnyAuthority('A','U')")
    @GetMapping("/get")
    public ResponseEntity<List<ShowDTO>> get() {
        try{
            List<ShowDTO> returnList = new ArrayList<>();
            returnList = showService.getShows();
            ResponseEntity<List<ShowDTO>> returnvalue; returnvalue = new ResponseEntity<>(returnList,HttpStatusCode.valueOf(200));
            return  returnvalue;
        }
        catch (Exception e){
            List<ShowDTO> returnList = new ArrayList<>();
            ResponseEntity<List<ShowDTO>> returnvalue; returnvalue = new ResponseEntity<>(returnList,HttpStatusCode.valueOf(500));
            return returnvalue;
        }
    }

    //TODO - Test this out
    @PreAuthorize("hasAuthority('A')")
    @PostMapping("/add/{userId}")
    public ResponseEntity<String> add(@RequestBody List<ShowDTO> addDetails, @PathVariable Long userId) {
        try {
            showService.addShow(addDetails,userId);
            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            String retval = "Error at Adding Event " + e.getMessage();
            return new ResponseEntity<>(retval,HttpStatusCode.valueOf(500));
        }
    }

    @PreAuthorize("hasAuthority('A')")
    @PostMapping("/modify")
    public ResponseEntity<String> modify(Map<String, String> modifyDetails) {
        try{
            showService.updateShow(modifyDetails);
            return new ResponseEntity<>("success",HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            String retval = "Error at Modifying Event " + e.getMessage();
            return new ResponseEntity<>(retval,HttpStatusCode.valueOf(500));
        }
    }

    @PreAuthorize("hasAuthority('A')")
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(ShowDTO deleteDetails) {
        try {
            showService.deleteShow(deleteDetails);
            return new ResponseEntity<>("Successful",HttpStatusCode.valueOf(200));
        }
        catch (Exception e){
            String retval = "Error at Modifying Event " + e.getMessage();
            return new ResponseEntity<>(retval,HttpStatusCode.valueOf(500));
        }
    }

    @PreAuthorize("hasAnyAuthority('A','U')")
    @GetMapping("/get/{eventId}")
    public ResponseEntity<List<AllDetailsDTO>> getByName(@PathVariable Long eventId){
        try{
            List<AllDetailsDTO> returnvalue = showService.getShowsByEvent(eventId);
            return new ResponseEntity<>(returnvalue,HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            System.out.println("Error in getting show with eventId "+e.getMessage());
            List<AllDetailsDTO> returnValue = new ArrayList<>();
            return new ResponseEntity<>(returnValue,HttpStatusCode.valueOf(500));
        }
    }

}
