package com.example.demo.Service;

import com.example.demo.Models.DTO.*;
import com.example.demo.Models.SeatStatusEnum;
import com.example.demo.Repository.*;
import com.example.demo.SeatNotAvailableException;
import com.example.demo.Tables.*;
import jdk.jfr.Event;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class SeatAllocationService {
    private final SeatRepo seatRepo;
    private final BookingRepo bookingRepo;
    private final UserRepo userRepo;
    private final ShowRepo showRepo;
    private final SeatAllocationRepo seatAllocationRepo;

    public SeatAllocationService(SeatRepo seatRepo, BookingRepo bookingRepo, UserRepo userRepo, SeatAllocationRepo seatAllocationRepo, ShowRepo showRepo){
        this.showRepo = showRepo;
        this.seatRepo = seatRepo;
        this.bookingRepo = bookingRepo;
        this.userRepo = userRepo;
        this.seatAllocationRepo = seatAllocationRepo;
    }

    @Transactional
    public AllDetailsDTO confirmSeat(SeatAvailabilityDTO toLockSeatsDetails) {
        BookingDTO bookingDTO = new BookingDTO();
        List<SeatDTO> seatList = new ArrayList<>();
        List<SeatAllocationEntity> seatEntities = seatAllocationRepo.findLockedSeatsByUser(toLockSeatsDetails.getShowId(),toLockSeatsDetails.getSeatId(),toLockSeatsDetails.getUserId(), SeatStatusEnum.pending, LocalDateTime.now());
        BigDecimal seatPrice = BigDecimal.ZERO;
        if(seatEntities.size() != toLockSeatsDetails.getSeatId().size()){
            throw new SeatNotAvailableException("Seats have been expired due to time");
        }
        for(SeatAllocationEntity seatEntity: seatEntities){
            seatList.add(seatEntity.getSeat().convertToDTO());
            seatEntity.setLockExpiry(null);
            seatEntity.setStatus(SeatStatusEnum.booked);
            seatPrice = seatPrice.add(seatEntity.getSeat().getSeatPrice());
        }

        BookingEntity booking = bookingSeat(seatPrice, toLockSeatsDetails);
        bookingDTO.setTotalAmount(seatPrice);
        bookingDTO.setId(booking.getId());
        bookingDTO.setSeats(seatList);
        bookingDTO.setCreatedAt(booking.getCreatedAt());

        SeatAllocationEntity seat = seatEntities.get(0);

        VenueDTO venueDTO = seat.getSeat().getScreen().getVenue().convertToDTO();
        ScreenDTO screenDTO = seat.getSeat().getScreen().convertToDTO();
        LocationDetailsDTO locationDetailsDTO = new LocationDetailsDTO(venueDTO,screenDTO);
        ShowDTO showDTO =  seat.getShow().convertToDTO();
        EventDTO eventDTO = seat.getShow().getEventEntity().convertToDTO();
        AllDetailsDTO allDetailsDTO = new AllDetailsDTO(locationDetailsDTO,eventDTO,showDTO,bookingDTO);
        return allDetailsDTO;
    }

    public BookingEntity bookingSeat(BigDecimal seatPrice, SeatAvailabilityDTO lockedSeatInformation){
        User user = userRepo.getReferenceById(lockedSeatInformation.getUserId());
        ShowEntity show = showRepo.getReferenceById(lockedSeatInformation.getShowId());
        return bookingRepo.save(new BookingEntity(user,show,seatPrice,LocalDateTime.now()));
    }


    @Transactional
    public void lockSeat(SeatAvailabilityDTO toLockSeatsDetails) {
        Collections.sort(toLockSeatsDetails.getSeatId());
        List<SeatAllocationEntity> lockedSeats = seatAllocationRepo.findAllByIdWithLock(toLockSeatsDetails.getShowId(),toLockSeatsDetails.getSeatId());

        if (lockedSeats.size() != toLockSeatsDetails.getSeatId().size()) {
            throw new RuntimeException("One or more seat IDs are invalid for this show.");
        }

        for (SeatAllocationEntity seat : lockedSeats) {
            if (seat.getStatus() != SeatStatusEnum.available) {
                throw new SeatNotAvailableException("Seat " + seat.getSeat().getSeatName() + " is already " + seat.getStatus() +" "+ seat.getSeat().getId());
            }
        }

        User userWhoRequestedLock = userRepo.getReferenceById(toLockSeatsDetails.getUserId());
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(10);
        for (SeatAllocationEntity seat : lockedSeats) {
            seat.setLockExpiry(expiry);
            seat.setStatus(SeatStatusEnum.pending);
            seat.setUser(userWhoRequestedLock);
        }
        seatAllocationRepo.saveAll(lockedSeats);
    }

    public void createSeat(List<ShowDTO> addDetails, List<ShowEntity> savedShows , Long userId) {
        List<SeatAllocationEntity> seatAllocationEntities = new ArrayList<>();
        Map<Long,List<SeatEntity>> cache = new HashMap<>();
        for(int i=0; i<addDetails.size(); i++){
            ShowDTO currentProcessedShow = addDetails.get(i);
            List<SeatEntity> seatEntitiesToAdd = cache.computeIfAbsent(
                    currentProcessedShow.getScreenId(),
                    seatRepo::findAllByScreenId
            );
            for(SeatEntity seats: seatEntitiesToAdd){
                SeatAllocationEntity seatAllocationEntity = new SeatAllocationEntity(seats, SeatStatusEnum.available,savedShows.get(i));
                seatAllocationEntities.add(seatAllocationEntity);
            }
        }
        seatAllocationRepo.saveAll(seatAllocationEntities);
    }

    @Scheduled(fixedRate = 180 * 1000)
    public void deleteSeat(){
        int deleted = seatAllocationRepo.releaseLockedSeats(LocalDateTime.now());
        System.out.println("Locks released = "+deleted);
    }
}
