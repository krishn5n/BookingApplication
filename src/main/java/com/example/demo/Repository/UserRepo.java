package com.example.demo.Repository;

import com.example.demo.Tables.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long>{
    User findByName(String name);
    User findByEmail(String email);
}
