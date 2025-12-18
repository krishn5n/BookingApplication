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
    private String refreshToken;

    public UserDTO(UserRole role, String password_hash, String password, String email, String phone, String name) {
        this.role = role;
        this.password_hash = password_hash;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.name = name;
    }

    public User convertToUserEntity(){
        return new User(name,phone,email,password_hash,role,refreshToken);
    }
}
