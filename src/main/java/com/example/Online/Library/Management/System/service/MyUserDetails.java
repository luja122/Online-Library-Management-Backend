package com.example.Online.Library.Management.System.service;

import com.example.Online.Library.Management.System.model.UserPrinciple;
import com.example.Online.Library.Management.System.model.Users;
import com.example.Online.Library.Management.System.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetails implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {
   Users user = userRepo.findByGmail(input).orElseThrow(()-> new UsernameNotFoundException("User not Found"));
   return new UserPrinciple(user);
    }
}
