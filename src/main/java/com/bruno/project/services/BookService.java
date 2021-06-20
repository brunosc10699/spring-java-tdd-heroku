package com.bruno.project.services;

import com.bruno.project.dto.BookDTO;
import com.bruno.project.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Transactional(readOnly = true)
    public Page<BookDTO> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).map(BookDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<BookDTO> findByTitleIgnoreCase(String text, Pageable pageable){
        return bookRepository.findByTitleIgnoreCase(text, pageable).map(BookDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<BookDTO> findByLanguageIgnoreCase(String text, Pageable pageable){
        return bookRepository.findByLanguageIgnoreCase(text, pageable).map(BookDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<BookDTO> findByPublisherIgnoreCase(String text, Pageable pageable){
        return bookRepository.findByPublisherIgnoreCase(text, pageable).map(BookDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<BookDTO> findByAuthorIgnoreCase(String text, Pageable pageable){
        return bookRepository.findByAuthorIgnoreCase(text, pageable).map(BookDTO::new);
    }

}
