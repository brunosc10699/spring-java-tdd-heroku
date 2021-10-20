package com.bruno.project.services;

import com.bruno.project.dto.AuthorDTO;
import com.bruno.project.dto.BookDTO;
import com.bruno.project.entities.Author;
import com.bruno.project.entities.Book;
import com.bruno.project.repositories.AuthorRepository;
import com.bruno.project.repositories.BookRepository;
import com.bruno.project.services.exceptions.AuthorNotFoundException;
import com.bruno.project.services.exceptions.BookAlreadyRegisteredException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface BookService {

    Page<BookDTO> findAll(Pageable pageable);

    Page<BookDTO> findByTitleContainingIgnoreCase(String text, Pageable pageable);

    Page<BookDTO> findByLanguageContainingIgnoreCase(String text, Pageable pageable);

    Page<BookDTO> findByPublisherContainingIgnoreCase(String text, Pageable pageable);

    Page<BookDTO> findBooksByAuthorName(String author, Pageable pageable);

    BookDTO save(BookDTO bookDTO);

    BookDTO updateById(Long id, BookDTO bookDTO);

    void deleteById(Long id);
}
