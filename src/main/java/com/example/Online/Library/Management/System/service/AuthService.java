package com.example.Online.Library.Management.System.service;

import com.example.Online.Library.Management.System.model.Users;
import com.example.Online.Library.Management.System.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired JwtService jwtService;
@Autowired
private UserRepo userRepo;
private BCryptPasswordEncoder encoder= new BCryptPasswordEncoder(12);
    public Users registerUser(Users detail) {
        detail.setPassword(encoder.encode(detail.getPassword()));
        return userRepo.save(detail);
    }
    public String verifyUser(Users details) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(details.getGmail(),details.getPassword()));
        if(auth.isAuthenticated()){
           return jwtService.generateToken(details.getGmail());
        }
        return "Failed";
    }
}
