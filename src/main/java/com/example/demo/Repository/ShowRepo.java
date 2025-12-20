package com.example.demo.Repository;

import com.example.demo.Tables.ShowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowRepo extends JpaRepository<ShowEntity,Long> {
    List<ShowEntity> findByEventEntityId(Long eventId);
}
