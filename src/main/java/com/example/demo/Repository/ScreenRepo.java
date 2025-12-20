package com.example.demo.Repository;

import com.example.demo.Tables.ScreenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreenRepo extends JpaRepository<ScreenEntity,Long> {
}
