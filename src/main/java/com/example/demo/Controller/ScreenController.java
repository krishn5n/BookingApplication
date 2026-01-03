package com.example.demo.Controller;

import com.example.demo.Models.DTO.ScreenDTO;
import com.example.demo.Service.ScreenService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/screen")
public class ScreenController {
    private final ScreenService screenService;

    public ScreenController(ScreenService screenService){
        this.screenService = screenService;
    }

    @PreAuthorize("hasAnyAuthority('U','A')")
    @GetMapping("/get")
    public ResponseEntity<List<ScreenDTO>> get() {
        try {
            List<ScreenDTO> retrunList = screenService.getScreen();
            return new ResponseEntity<>(retrunList, HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            List<ScreenDTO> returnList = new ArrayList<>();
            return new ResponseEntity<List<ScreenDTO>>(returnList,HttpStatusCode.valueOf(500));
        }
    }

    @PreAuthorize("hasAuthority('A')")
    @PostMapping("/add")
    public ResponseEntity<String> add(ScreenDTO addDetails) {
        try {
            screenService.addScreen(addDetails);
            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            String retval = "Error at Adding Event " + e.getMessage();
            return new ResponseEntity<>(retval,HttpStatusCode.valueOf(500));
        }
    }


    @PreAuthorize("hasAuthority('A')")
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(ScreenDTO deleteDetails) {
        try {
            screenService.deleteScreen(deleteDetails);
            return new ResponseEntity<>("Successful",HttpStatusCode.valueOf(200));
        }
        catch (Exception e){
            String retval = "Error at Modifying Event " + e.getMessage();
            return new ResponseEntity<>(retval,HttpStatusCode.valueOf(500));
        }
    }
}
