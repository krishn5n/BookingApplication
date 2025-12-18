package com.example.demo.Controller;

import com.example.demo.Models.UserDTO;
import com.example.demo.Service.JWTService;
import com.example.demo.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;
    private final JWTService jwtService;

    public UserController(UserService obj, JWTService jwtService){
        this.userService = obj;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody UserDTO userdata){
        System.out.printf("At Singup Controller we have"+userdata);
        return userService.signupService(userdata);
    }

    @PostMapping("/signin")
    public String signIn(@RequestBody UserDTO userDetails){
        return userService.signInFunction(userDetails);
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(@RequestBody UserDTO userDTO) {
        String newAccessToken = jwtService.refreshAccessToken(userDTO);
        if(newAccessToken==null){
            ResponseEntity.status(500);
        }
        return ResponseEntity.ok(newAccessToken);
    }
}
