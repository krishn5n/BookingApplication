package com.example.demo.Service;
import com.example.demo.Models.UserPrincipleInfo;
import com.example.demo.Repository.UserRepo;
import com.example.demo.Tables.User;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepo userRepo;

    public CustomUserDetailService(UserRepo userRepo){
        this.userRepo = userRepo;
    }


    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        User userData =  userRepo.findByEmail(username);
        if(userData == null){
            throw new UsernameNotFoundException("User not found");
        }
        return new UserPrincipleInfo(userData);
    }
}
