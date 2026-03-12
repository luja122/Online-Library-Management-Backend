package com.example.Online.Library.Management.System.repo;

import com.example.Online.Library.Management.System.model.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepo extends JpaRepository<Books, Integer> {
}
