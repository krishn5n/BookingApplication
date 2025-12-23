package com.example.demo.Controller;

import com.example.demo.Models.DTO.UserDTO;
import com.example.demo.Models.TokenObject;
import com.example.demo.Service.JWTService;
import com.example.demo.Service.UserService;
import org.springframework.http.HttpStatusCode;
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
    public ResponseEntity<TokenObject> signup(@RequestBody UserDTO userdata){
        try {
            System.out.printf("At Singup Controller we have" + userdata);
            TokenObject returnValue = userService.signupService(userdata);
            if( returnValue == null || returnValue.getRefreshToken() == null || returnValue.getAccessToken() == null){
                throw new Exception("There is some error in signup as JWT is empty");
            }
            return new ResponseEntity<>(returnValue, HttpStatusCode.valueOf(200));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new TokenObject("",""), HttpStatusCode.valueOf(500));
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenObject> signIn(@RequestBody UserDTO userDetails){
        try {
            TokenObject returnValue= userService.signInFunction(userDetails);
            if(returnValue == null){
                throw new Exception("There is some error in signup as JWT is empty");
            }
            return new ResponseEntity<>(returnValue, HttpStatusCode.valueOf(200));

        } catch (Exception e) {
            return new ResponseEntity<>(new TokenObject("",""), HttpStatusCode.valueOf(500));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenObject> refresh(@RequestBody UserDTO userDTO) {
        try {
            String newAccessToken = jwtService.refreshAccessToken(userDTO);
            if (newAccessToken.isEmpty()) {
                return new ResponseEntity<>(new TokenObject("",""),HttpStatusCode.valueOf(401));
            }
            return new ResponseEntity<>(new TokenObject(newAccessToken,""),HttpStatusCode.valueOf(200));
        }
        catch (Exception e){
            return new ResponseEntity<>(new TokenObject("",""),HttpStatusCode.valueOf(500));
        }
    }
}
