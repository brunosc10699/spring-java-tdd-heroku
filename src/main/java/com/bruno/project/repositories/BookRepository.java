package com.bruno.project.repositories;

import com.bruno.project.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Transactional(readOnly = true)
    Page<Book> findByTitleContainingIgnoreCase(String text, Pageable pageable);

    @Transactional(readOnly = true)
    Page<Book> findByLanguageContainingIgnoreCase(String text, Pageable pageable);

    @Transactional(readOnly = true)
    Page<Book> findByPublisherContainingIgnoreCase(String text, Pageable pageable);

    @Transactional(readOnly = true)
    Optional<Book> findByIsbn(String isbn);
}
