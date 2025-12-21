package com.example.demo.Repository;

import com.example.demo.Tables.EventEntity;
import com.example.demo.Tables.ShowEntity;
import jdk.jfr.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowRepo extends JpaRepository<ShowEntity,Long> {
    List<ShowEntity> findByEventEntity(EventEntity EventId);

    @Query("SELECT s FROM Shows s JOIN FETCH s.eventEntity e JOIN FETCH s.screenEntity sc JOIN FETCH sc.venue v WHERE e.id = :eventId")
    List<ShowEntity> findAllDetailsUsingEventId(@Param("eventId") Long eventId);

}
