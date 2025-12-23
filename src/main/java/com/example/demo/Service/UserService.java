package com.example.demo.Service;

import com.example.demo.Models.DTO.UserDTO;
import com.example.demo.Models.TokenObject;
import com.example.demo.Repository.UserRepo;
import com.example.demo.Tables.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

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

    public TokenObject signupService(UserDTO userdata){
        try{
            userdata.setPassword_hash(passwordHasher.encode(userdata.getPassword()));
            User rowValue = userdata.convertToUserEntity();
            String refreshToken = jwtService.createRefreshToken(userdata.getEmail());
            rowValue.setRefreshToken(refreshToken);
            userRepo.save(rowValue);
            String accessToken = jwtService.createAccessToken(userdata.getEmail(),userdata.getRole().name());
            return new TokenObject(accessToken,refreshToken);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public TokenObject signInFunction(UserDTO userData){
        Authentication authentication =  authManager.authenticate(new UsernamePasswordAuthenticationToken(userData.getEmail(), userData.getPassword()));
        System.out.println(authentication.getAuthorities());
        String roleName = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).filter(Objects::nonNull).filter(auth -> !auth.startsWith("FACTOR_")).toList().get(0);
        String refreshToken = jwtService.createRefreshToken(userData.getEmail());
        String accessToken = jwtService.createAccessToken(userData.getEmail(),roleName);
        User user = userRepo.findByEmail(userData.getEmail());
        user.setRefreshToken(refreshToken);
        return new TokenObject(accessToken,refreshToken);
    }

}
