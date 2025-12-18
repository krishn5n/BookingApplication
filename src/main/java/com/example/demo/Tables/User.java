package com.example.demo.Tables;

import com.example.demo.Models.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "User")
@Table(name="users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,name="name")
    private String name;

    @Column(nullable = false, unique = true, name="phone")
    private String phone;

    @Column(nullable = false, unique = true, name="email")
    private String email;

    @Column(nullable = false, name="password_hash")
    private String passwordHash;

    @Column(nullable = false, name="role", columnDefinition = "user_role")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private UserRole role;

    @Column(nullable = false, name = "refresh_token")
    private String refreshToken;

    public User(String name, String phone, String email, String passwordHash, UserRole role, String refreshToken ){
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.refreshToken = refreshToken;
    }
}
