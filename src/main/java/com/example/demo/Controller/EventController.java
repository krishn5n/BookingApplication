package com.example.demo.Controller;

import com.example.demo.Models.EventDTO;
import com.example.demo.Service.EventService;
import com.example.demo.Tables.EventEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService){
        this.eventService = eventService;
    }

    @PreAuthorize("hasAnyAuthority('U','A')")
    @GetMapping("/get")
    public ResponseEntity<List<EventEntity>> getEvents(){
        try {
            List<EventEntity> retrunList = eventService.getEvents();
            return new ResponseEntity<>(retrunList,HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            List<EventEntity> returnList = new ArrayList<>();
            return new ResponseEntity<List<EventEntity>>(returnList,HttpStatusCode.valueOf(500));
        }
    }

    @PreAuthorize("hasAuthority('A')")
    @PostMapping("/add")
    public ResponseEntity<String> addEvent(@RequestBody EventDTO eventDetails){
        try {
            eventService.addEvent(eventDetails);
            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            String retval = "Error at Adding Event " + e.getMessage();
            return new ResponseEntity<>(retval,HttpStatusCode.valueOf(500));
        }
    }

    @PreAuthorize("hasAuthority('A')")
    @PostMapping("/modify")
    public ResponseEntity<Boolean> modifyEvent(@RequestBody Map<String,String> eventDetails){
        try{
            eventService.updateEvent(eventDetails);
            return new ResponseEntity<>(true,HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(false,HttpStatusCode.valueOf(500));
        }
    }

    @PreAuthorize("hasAuthority('A')")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteEvent(@RequestBody EventDTO eventDetails){
        try {
            System.out.println(eventDetails);
            eventService.deleteEvent(eventDetails);
            return new ResponseEntity<>("Successful",HttpStatusCode.valueOf(200));
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatusCode.valueOf(500));
        }
    }
}
