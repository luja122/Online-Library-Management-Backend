package com.example.Online.Library.Management.System.controller;

import com.example.Online.Library.Management.System.model.Books;
import com.example.Online.Library.Management.System.service.BookService;
import com.example.Online.Library.Management.System.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @PostMapping(value ="/addBook",consumes = "multipart/form-data")
    public ResponseEntity<?> addBook(@RequestPart("book") Books book, @RequestPart("image") MultipartFile image) throws IOException {
    try{
        Books books = bookService.addBook(book,image );
        System.out.println(books);
        return ResponseEntity.ok(books);
    }catch (Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }
    @PostMapping("/deletebook/{bookid}")
    public ResponseEntity<?> deleteBook(@PathVariable int bookid){
        return new ResponseEntity<>(bookService.deleteBook(bookid),HttpStatus.OK);
    }
    @PostMapping("/deleteUser/{Userid}")
    public ResponseEntity<?> delteUser(@PathVariable int userid){
        return new ResponseEntity<>(userService.deletUser(userid),HttpStatus.OK);
    }
}


