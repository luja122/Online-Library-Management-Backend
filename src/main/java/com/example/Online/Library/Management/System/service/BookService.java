package com.example.Online.Library.Management.System.service;

import com.example.Online.Library.Management.System.model.Books;
import com.example.Online.Library.Management.System.model.Borrow;
import com.example.Online.Library.Management.System.model.Users;
import com.example.Online.Library.Management.System.repo.BookRepo;
import com.example.Online.Library.Management.System.repo.BorrowRepo;
import com.example.Online.Library.Management.System.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDate;

import java.util.List;

@Service
public class BookService {
  @Autowired
  private BookRepo bookRepo;
  @Autowired
  private BorrowRepo borrowRepo;
   @Autowired
   private UserRepo userRepo;
    public ResponseEntity<?> borrowBook(int bookId, int userId) {
       Books book = bookRepo.findById(bookId).orElseThrow(()-> new UsernameNotFoundException("User not found"));
      int num_book_available= book.getNo_book();
        if(num_book_available<=0){
            if(num_book_available==0){
                book.setStatus("unavailable");
            }
            return ResponseEntity.badRequest().body("No books available");
        }
        Users user = userRepo.findById(userId).orElseThrow(()->new UsernameNotFoundException("User not Found"));
        Books book1 = bookRepo.findById(bookId).orElseThrow(()-> new UsernameNotFoundException("Book not Found"));
       int new_num_book_available = num_book_available-1;
        book.setNo_book(new_num_book_available);
       Borrow data = new Borrow();
   data.setBorrow_date(LocalDate.now());
   data.setUser(user);
   data.setBook(book1);
   data.setReturn_date(LocalDate.now().plusDays(7));
   borrowRepo.save(data);

   return ResponseEntity.ok(data);
    }

    public List<Books> getAllBooks() {
        List<Books> bookDetails = bookRepo.findAll();
        System.out.println(bookDetails);
        return bookDetails;
    }

    public Books getBookByid(int id) {
       return bookRepo.findById(id).orElseThrow(()-> new UsernameNotFoundException("id not Found"));
    }
    public Books addBook(Books book, MultipartFile image) throws IOException {
     book.setImage_Name(image.getOriginalFilename());
     book.setImage_Data(image.getBytes());
     book.setImage_Type(image.getContentType());
     return bookRepo.save(book);
    }

    public ResponseEntity<String> deleteBook(int bookid) {
        if(borrowRepo.existsByBook_Id(bookid)){
            return ResponseEntity.badRequest().body("Booked is borrowed cannot delete currently");
        }else{
            bookRepo.deleteById(bookid);
            return ResponseEntity.ok("Deleted Sucessfully");
        }
    }


    public ResponseEntity<?> returnBook(int bookId, int userId) {
        if(borrowRepo.existsByBook_Id(bookId)&&borrowRepo.existsByUser_Id(userId)){
         int book =  borrowRepo.findByBook_id(bookId).orElseThrow(()->new UsernameNotFoundException("User not found")).getId();
            borrowRepo.deleteById(book);
            int nobook= bookRepo.findById(bookId).orElseThrow(()->new UsernameNotFoundException("Book not found")).getNo_book();
            nobook=nobook+1;
        return  ResponseEntity.ok("Deleted Sucessfully") ;
        }else{
            return ResponseEntity.badRequest().body("Cannot Delete");
        }
    }

    public List <Books> get_list_Borrowed_Book() {
        List <Books> borrowedBook= borrowRepo.findAll().stream().map((books -> books.getBook())).toList();
        return borrowedBook;
    }
}
