package com.example.demo.Service;

import com.example.demo.Models.DTO.SeatDTO;
import com.example.demo.Repository.SeatRepo;
import com.example.demo.Tables.SeatEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatService {
    private final SeatRepo seatRepo;

    public SeatService(SeatRepo seatRepo){
        this.seatRepo = seatRepo;
    }

    public List<SeatDTO> getSeatByScreenId(Long screenId) {
           List<SeatEntity> resultOfRepo = seatRepo.findAllByScreenId(screenId);
           List<SeatDTO> returnValue = new ArrayList<>();
           for(SeatEntity seatEntity:resultOfRepo){
               returnValue.add(seatEntity.convertToDTO());
           }
           return returnValue;
    }
}
