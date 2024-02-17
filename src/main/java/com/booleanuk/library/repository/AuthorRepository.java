package com.booleanuk.library.repository;

import com.booleanuk.library.model.Author;
import com.booleanuk.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
