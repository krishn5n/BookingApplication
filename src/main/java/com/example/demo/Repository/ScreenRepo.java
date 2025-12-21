package com.example.demo.Repository;

import com.example.demo.Tables.ScreenEntity;
import com.example.demo.Tables.VenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScreenRepo extends JpaRepository<ScreenEntity,Long> {

    List<ScreenEntity> findByVenue_Id(Long id);
}
