package com.example.demo.Repository;

import com.example.demo.Models.DTO.SeatDTO;
import com.example.demo.Tables.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepo extends JpaRepository<SeatEntity,Long> {
    List<SeatEntity> findAllByScreenId(Long screenId);
}
