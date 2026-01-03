package com.example.demo.Controller;

import com.example.demo.Models.DTO.ScreenDTO;
import com.example.demo.Models.DTO.SeatDTO;
import com.example.demo.Service.SeatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/seat")
public class SeatController {
    private final SeatService seatService;

    public SeatController(SeatService seatService){
        this.seatService = seatService;
    }

    //TODO - Handle this to show which seats are booked
    @PreAuthorize("hasAnyAuthority('A','U')")
    @GetMapping("/get/{screenId}")
    public ResponseEntity<List<SeatDTO>> getSeats(@PathVariable Long screenId){
        try{
            List<SeatDTO> returnValue = seatService.getSeatByScreenId(screenId);
            return new ResponseEntity<List<SeatDTO>>(returnValue,HttpStatusCode.valueOf(200));
        }
        catch (Exception e){
            System.out.println("Error at Seat - Get/ScreenID - "+e.getMessage());
            return new ResponseEntity<List<SeatDTO>>(new ArrayList<>(), HttpStatusCode.valueOf(500));
        }
    }

    @PreAuthorize("hasAuthority('A')")
    @PostMapping("/add/{screenId}")
    public ResponseEntity<String> addSeatsForScreen(@PathVariable Long screenId, @RequestBody List<SeatDTO> addDetail){
        try{
            seatService.addSeats(screenId,addDetail);
            return new ResponseEntity<String>("Cools",HttpStatusCode.valueOf(200));
        }
        catch (Exception e){
            return new ResponseEntity<String>("Error at adding seats "+e.getMessage(),HttpStatusCode.valueOf(500));
        }
    }
}
