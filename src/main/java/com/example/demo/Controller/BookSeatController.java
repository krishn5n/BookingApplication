package com.example.demo.Controller;

import com.example.demo.Models.DTO.AllDetailsDTO;
import com.example.demo.Models.DTO.SeatAvailabilityDTO;
import com.example.demo.Service.BookingService;
import com.example.demo.Service.SeatAllocationService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookSeatController {
    private final SeatAllocationService bookSeatService;
    private final BookingService bookingService;

    public BookSeatController(SeatAllocationService bookSeatService, BookingService bookingService) {
        this.bookSeatService = bookSeatService;
        this.bookingService = bookingService;
    }

    @PreAuthorize("hasAnyAuthority('A','U')")
    @PostMapping("/lock")
    //In the DTO we get List of SeatId, ShowId, UserId -> Thats it
    public ResponseEntity<String> lockSeats(@RequestBody SeatAvailabilityDTO lockSeatList){
        try{
            bookSeatService.lockSeat(lockSeatList);
            return new ResponseEntity<>("Success", HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>("Error at /book/lock "+e.getMessage(), HttpStatusCode.valueOf(500));
        }
    }

    @PreAuthorize("hasAnyAuthority('U','A')")
    @PostMapping("/confirm")
    public ResponseEntity<String> confirmSeat(@RequestBody SeatAvailabilityDTO confirmSeatList){
        try{
            bookSeatService.confirmSeat(confirmSeatList);
            return new ResponseEntity<>("Booking Successful", HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>("Booking Failed as eror "+e.getMessage(), HttpStatusCode.valueOf(500));
        }
    }

    @PreAuthorize("hasAnyAuthority('U','A')")
    @GetMapping("/{userId}")
    public ResponseEntity<List<AllDetailsDTO>> getUserBookings(@PathVariable Long userId){
        try{
            List<AllDetailsDTO> userBookings = bookingService.getUserBookings(userId);
            return new ResponseEntity<>(userBookings,HttpStatusCode.valueOf(200));
        }
        catch (Exception e){
            return new ResponseEntity<>(new ArrayList<AllDetailsDTO>(),HttpStatusCode.valueOf(500));
        }
    }
}
