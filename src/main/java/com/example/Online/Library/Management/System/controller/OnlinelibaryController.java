package com.example.Online.Library.Management.System.controller;

import com.example.Online.Library.Management.System.model.Books;
import com.example.Online.Library.Management.System.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class OnlinelibaryController {
  @Autowired
  private BookService bookService;
  @GetMapping("/index")
  public ResponseEntity<List<Books>> getAllBooks(){
    List<Books> books= bookService.getAllBooks();
    return ResponseEntity.ok(books);
  }
  @GetMapping("/index/{id}")
  public ResponseEntity<Books> getBookByid(@PathVariable int id){
    Books book = bookService.getBookByid(id);
    return ResponseEntity.ok(book);
  }

  @PostMapping("/borrow/{bookId}/{userId}")
  public ResponseEntity<?> borrowBook(
          @PathVariable int bookId,
          @PathVariable int userId) {
    ResponseEntity<?> data = bookService.borrowBook(bookId, userId);

    if (data == null) {
      return ResponseEntity.badRequest().body("Book cannot be borrowed");
    }
    return ResponseEntity.ok(data);
  }
}

