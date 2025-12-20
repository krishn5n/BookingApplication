package com.example.demo.Service;

import com.example.demo.Models.DTO.UserDTO;
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
            System.out.println("Password hash set "+userdata.getPassword_hash());
            User rowValue = userdata.convertToUserEntity();
            System.out.println("Converted to user entity");
            rowValue.setRefreshToken(jwtService.createRefreshToken(userdata.getEmail()));
            System.out.println("Made a refresh token");
            userRepo.save(rowValue);
            System.out.println("Row value saved");
            return jwtService.createAccessToken(userdata.getEmail(),userdata.getRole().name());
        }
        catch (Exception e){
            System.out.println(e);
            return "";
        }
    }

    public String signInFunction(UserDTO userData){
        try{
            Authentication authentication =  authManager.authenticate(new UsernamePasswordAuthenticationToken(userData.getEmail(), userData.getPassword()));
            String roleName = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).filter(auth -> !auth.startsWith("FACTOR_")).toList().get(0);
            String refreshToken = userData.getRefreshToken();
            if (!authentication.isAuthenticated()) {
                return null;
            }

            if(!jwtService.isTokenExpired(refreshToken)) {
                    return jwtService.createAccessToken(userData.getEmail(), roleName);
            }
            else{
                String newRefreshToken = jwtService.createRefreshToken(userData.getEmail());
                User rowValue = userData.convertToUserEntity();
                rowValue.setRefreshToken(newRefreshToken);
                return jwtService.createAccessToken(userData.getEmail(), roleName);
            }
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

}
