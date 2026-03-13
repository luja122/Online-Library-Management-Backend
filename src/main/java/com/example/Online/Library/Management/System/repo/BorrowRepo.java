package com.example.Online.Library.Management.System.repo;
import com.example.Online.Library.Management.System.model.Borrow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowRepo extends JpaRepository<Borrow,Integer>{
boolean existsByBook_Id(  Integer book_id);
boolean existsByUser_Id( Integer user_id);
Optional<Borrow> findByBook_id(int book_id);
}
