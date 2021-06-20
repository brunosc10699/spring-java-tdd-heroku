package com.bruno.project.repositories;

import com.bruno.project.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findByTitleIgnoreCase(String text, Pageable pageable);

    Page<Book> findByLanguageIgnoreCase(String text, Pageable pageable);

    Page<Book> findByPublisherIgnoreCase(String text, Pageable pageable);

    Page<Book> findByAuthorIgnoreCase(String text, Pageable pageable);

    Optional<Book> findByIsbn(String isbn);
}
