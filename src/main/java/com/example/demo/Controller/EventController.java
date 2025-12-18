package com.example.demo.Controller;

import com.example.demo.Models.EventDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
public class EventController {

    @PreAuthorize("hasAuthority('A')")
    @PostMapping("/add")
    public void addEvent(@RequestBody EventDTO eventDetails){
        System.out.println(eventDetails);
    }
}
