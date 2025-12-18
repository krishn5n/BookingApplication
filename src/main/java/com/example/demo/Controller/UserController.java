package com.example.demo.Controller;

import com.example.demo.Models.UserDTO;
import com.example.demo.Service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService obj){
        this.userService = obj;
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

    @PostMapping("/test")
    public void test(@RequestBody UserDTO userDetails){
        userService.test();
        return;
    }
}
