package com.example.demo.Service;

import com.example.demo.Models.DTO.SeatDTO;
import com.example.demo.Repository.ScreenRepo;
import com.example.demo.Repository.SeatRepo;
import com.example.demo.Tables.ScreenEntity;
import com.example.demo.Tables.SeatEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SeatService {
    private final SeatRepo seatRepo;
    private final ScreenRepo screenRepo;

    public SeatService(SeatRepo seatRepo, ScreenRepo screenRepo){
        this.seatRepo = seatRepo;
        this.screenRepo = screenRepo;
    }

    public List<SeatDTO> getSeatByScreenId(Long screenId) {
           List<SeatEntity> resultOfRepo = seatRepo.findAllByScreenId(screenId);
           List<SeatDTO> returnValue = new ArrayList<>();
           for(SeatEntity seatEntity:resultOfRepo){
               returnValue.add(seatEntity.convertToDTO());
           }
           return returnValue;
    }

    public void addSeats(Long screenId, List<SeatDTO> addDetail) {
        List<SeatEntity> seatEntities = new ArrayList<>();
        for(SeatDTO seatDTO:addDetail){
            ScreenEntity screenEntity = screenRepo.getReferenceById(screenId);
            SeatEntity seatEntity = new SeatEntity(seatDTO.getRowNumber(),seatDTO.getColNumber(),seatDTO.getSeatName(),seatDTO.getSeatPrice(),screenEntity);
            seatEntities.add(seatEntity);
        }
        seatRepo.saveAll(seatEntities);
    }


}
