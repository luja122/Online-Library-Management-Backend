package com.example.Online.Library.Management.System.service;

import com.example.Online.Library.Management.System.model.Users;
import com.example.Online.Library.Management.System.repo.BookRepo;
import com.example.Online.Library.Management.System.repo.BorrowRepo;
import com.example.Online.Library.Management.System.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo user;
    @Autowired
    private BorrowRepo borrowRepo;

    public ResponseEntity<String> deletUser(int userid) {
       if(borrowRepo.existsByUser_Id(userid)){
              return ResponseEntity.badRequest().body("User have Borrowed the book cannot delet the user");
       }else{
           return ResponseEntity.ok("Deleted UserSucessfully");
       }
    }
}
