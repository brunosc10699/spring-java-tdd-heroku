package com.bruno.project.services;

import com.bruno.project.dto.AuthorDTO;
import com.bruno.project.dto.BookDTO;
import com.bruno.project.entities.Author;
import com.bruno.project.entities.Book;
import com.bruno.project.repositories.BookRepository;
import com.bruno.project.services.exceptions.BookAlreadyRegisteredException;
import com.bruno.project.services.exceptions.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public BookDTO save(BookDTO bookDTO){
        checkRegisteredISBN(bookDTO.getIsbn());
        return new BookDTO(bookRepository.save(fromDTO(bookDTO)));
    }

    public BookDTO updateById(BookDTO bookDTO){
        checkRegisteredISBN(bookDTO.getIsbn());
        Book book = bookRepository.getById(bookDTO.getId());
        if(book != null){
            return new BookDTO(bookRepository.save(fromDTO(bookDTO)));
        }
        throw new BookNotFoundException(bookDTO.getId());
    }

    private void checkRegisteredISBN(String isbn){
        Optional<Book> book = bookRepository.findByIsbn(isbn);
        if(book.isPresent()){
            throw new BookAlreadyRegisteredException(isbn);
        }
    }

    private Book fromDTO(BookDTO bookDTO){
        return new Book(
                bookDTO.getId(),
                bookDTO.getIsbn(),
                bookDTO.getTitle(),
                bookDTO.getPrintLength(),
                bookDTO.getLanguage(),
                bookDTO.getPublicationYear(),
                bookDTO.getPublisher(),
                bookDTO.getUrlCover(),
                bookDTO.getBookGenre(),
                bookDTO.getAuthors().stream().map(Author::new).collect(Collectors.toList())
        );
    }
}
