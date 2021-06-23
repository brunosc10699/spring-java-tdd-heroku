package com.bruno.project.services;

import com.bruno.project.dto.BookDTO;
import com.bruno.project.entities.Book;
import com.bruno.project.repositories.BookRepository;
import com.bruno.project.services.exceptions.BookAlreadyRegisteredException;
import com.bruno.project.services.exceptions.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        bookDTO.setId(null);
        if(bookDTO.getUrlCover() == null)
            bookDTO.setUrlCover("https://live.staticflickr.com/65535/51264896706_e66beed079_n.jpg");
        checkRegisteredISBN(bookDTO.getIsbn());
        return new BookDTO(bookRepository.save(new Book(bookDTO)));
    }

    public BookDTO updateById(BookDTO bookDTO){
        checkRegisteredISBN(bookDTO.getIsbn());
        checkRegisteredId(bookDTO.getId());
        return new BookDTO(bookRepository.save(new Book(bookDTO)));
    }

    public void deleteById(Long id){
        if(checkRegisteredId(id)) bookRepository.deleteById(id);
    }

    private Boolean checkRegisteredId(Long id){
        Book book = bookRepository.getById(id);
        if(book == null) throw new BookNotFoundException(id);
        return true;
    }

    private void checkRegisteredISBN(String isbn){
        Optional<Book> book = bookRepository.findByIsbn(isbn);
        if(book.isPresent()) throw new BookAlreadyRegisteredException(isbn);
    }
}
