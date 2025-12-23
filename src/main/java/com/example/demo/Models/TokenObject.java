package com.example.demo.Models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenObject {
    private String accessToken;
    private String refreshToken;
}
