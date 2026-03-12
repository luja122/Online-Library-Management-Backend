package com.example.Online.Library.Management.System.controller;

import com.example.Online.Library.Management.System.model.Users;
import com.example.Online.Library.Management.System.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
@Autowired
private AuthService service;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Users detail){
    Users user =  service.registerUser(detail);
      return ResponseEntity.ok(user);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users details){
        String users = service.verifyUser(details);
        return ResponseEntity.ok(users);
    }

}
