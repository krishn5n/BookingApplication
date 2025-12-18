package com.example.demo.Service;

import com.example.demo.Models.UserDTO;
import com.example.demo.Repository.UserRepo;
import com.example.demo.Tables.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
public class UserService {
    private final UserRepo userRepo;
    private final AuthenticationManager authManager;
    private final BCryptPasswordEncoder passwordHasher = new BCryptPasswordEncoder(12);
    private final JWTService jwtService;

    public UserService(UserRepo userRepo, AuthenticationManager authManager, JWTService jwtService){
        this.userRepo = userRepo;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    public String signupService(UserDTO userdata){
        try{
            userdata.setPassword_hash(passwordHasher.encode(userdata.getPassword()));
            User rowValue = userdata.convertToUser();
            userRepo.save(rowValue);
            return jwtService.createAccessToken(userdata.getEmail(),userdata.getRole().name());
        }
        catch (Exception e){
            System.out.println(e);
            return "";
        }
    }

    public String signInFunction(UserDTO userDetails){
        try{
            Authentication authentication =  authManager.authenticate(new UsernamePasswordAuthenticationToken(userDetails.getEmail(), userDetails.getPassword()));
            String roleName = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).filter(auth -> !auth.startsWith("FACTOR_")).toList().get(0);
            if(authentication.isAuthenticated()){
                return jwtService.createAccessToken(userDetails.getEmail(),roleName);
            };
            return "";
        }
        catch(Exception e){
            System.out.println(e);
            return "";
        }
    }

    public void test() {
        System.out.println("Chumma");
    }
}
