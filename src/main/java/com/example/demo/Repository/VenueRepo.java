package com.example.demo.Repository;

import com.example.demo.Service.VenueService;
import com.example.demo.Tables.VenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VenueRepo extends JpaRepository<VenueEntity,Long> {
    public List<VenueEntity> findByNameContaining(String pattern);
}
