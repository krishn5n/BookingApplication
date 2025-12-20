package com.example.demo.Filter;

import com.example.demo.Repository.UserRepo;
import com.example.demo.Service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final ApplicationContext applicationContext;
    private final UserRepo userRepo;
    public JwtFilter(JWTService jwtService, ApplicationContext applicationContext, UserRepo userRepo){
        this.jwtService = jwtService;
        this.applicationContext = applicationContext;
        this.userRepo = userRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");
            String token = null;
            String email = null;

            //system.out.println("We at least here with request "+authHeader);

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                email = jwtService.getEmail(token);
            }

            //system.out.println(token+" "+email);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                //system.out.println("Email aint null and we in");
                //system.out.println("Token expiration is "+jwtService.isTokenExpired(token)+" validating claims for alter "+ jwtService.validateTokenClaims(token));
                if (!jwtService.isTokenExpired(token) && jwtService.validateTokenClaims(token)) {
                    String role = jwtService.getRoleFromToken(token);
                    //system.out.println(email+" "+token+" "+role);
                    UserDetails userDetails = User.builder()
                            .username(email)
                            .password("") // Not needed for stateless JWT
                            .authorities(role) // Role from token
                            .build();
                    UsernamePasswordAuthenticationToken nextToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    nextToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(nextToken);
                }
                else{
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"Access token expired\"}");
                    return;
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
