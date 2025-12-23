package com.example.demo.Service;

import com.example.demo.Models.DTO.*;
import com.example.demo.Repository.BookingRepo;
import com.example.demo.Tables.BookingEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepo bookingRepo;

    public BookingService(BookingRepo bookingRepo){
        this.bookingRepo = bookingRepo;
    }

    public List<AllDetailsDTO> getUserBookings(Long userId) {
        List<BookingEntity> bookingEntities = bookingRepo.findBookingsByUser(userId);
        List<AllDetailsDTO> returnValue = new ArrayList<>();
        for (BookingEntity bookingEntity : bookingEntities) {
            VenueDTO venue = bookingEntity.getShow().getScreenEntity().getVenue().convertToDTO();
            ScreenDTO screenDTO = bookingEntity.getShow().getScreenEntity().convertToDTO();
            LocationDetailsDTO locationDetailsDTO = new LocationDetailsDTO(venue, screenDTO);
            ShowDTO showDTO = bookingEntity.getShow().convertToDTO();
            EventDTO eventDTO = bookingEntity.getShow().getEventEntity().convertToDTO();
            BookingDTO bookingDTO = new BookingDTO(bookingEntity.getTotalAmount(), bookingEntity.getCreatedAt());
            AllDetailsDTO allDetailsDTO = new AllDetailsDTO(locationDetailsDTO, eventDTO, showDTO, bookingDTO);
            returnValue.add(allDetailsDTO);
        }
        return returnValue;
    }
}
