package com.example.demo;

import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TableCleanup{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PreDestroy
    public void truncateTableOnExit() {
        try {
            System.out.println("Application shutting down: Truncating table...");
            // TRUNCATE is faster and more efficient than DELETE for clearing a table
            jdbcTemplate.execute("TRUNCATE TABLE users");
            System.out.println("Table truncated successfully.");
        } catch (Exception e) {
            System.err.println("Failed to truncate table on shutdown: " + e.getMessage());
        }
    }
}