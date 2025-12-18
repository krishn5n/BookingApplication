package com.example.demo.Models;

import com.example.demo.Tables.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Used in case of getting the data in the request -> DOT

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String name;
    private String phone;
    private String email;
    private String password;
    private String password_hash;
    private UserRole role;

    public User convertToUser(){
        return new User(name,phone,email,password_hash,role);
    }
}
