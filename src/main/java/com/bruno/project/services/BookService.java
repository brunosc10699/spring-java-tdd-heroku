package com.bruno.project.services;

import com.bruno.project.dto.BookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
