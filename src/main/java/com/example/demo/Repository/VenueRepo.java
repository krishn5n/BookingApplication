package com.example.demo.Repository;

import com.example.demo.Tables.VenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepo extends JpaRepository<VenueEntity,Long> {
}
