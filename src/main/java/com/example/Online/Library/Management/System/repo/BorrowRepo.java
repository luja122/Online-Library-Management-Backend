package com.example.Online.Library.Management.System.repo;

import com.example.Online.Library.Management.System.model.Books;
import com.example.Online.Library.Management.System.model.Borrow;
import com.example.Online.Library.Management.System.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowRepo extends JpaRepository<Borrow,Integer>{
boolean existsByBook_Id(  Integer book_id);
boolean existsByUser_Id( Integer user_id);
}
