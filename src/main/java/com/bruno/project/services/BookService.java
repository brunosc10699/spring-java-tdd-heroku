package com.bruno.project.services;

import com.bruno.project.dto.AuthorDTO;
import com.bruno.project.dto.BookDTO;
import com.bruno.project.entities.Author;
import com.bruno.project.entities.Book;
import com.bruno.project.repositories.AuthorRepository;
import com.bruno.project.repositories.BookRepository;
import com.bruno.project.services.exceptions.AuthorNotFoundException;
import com.bruno.project.services.exceptions.BookAlreadyRegisteredException;
import com.bruno.project.services.exceptions.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Transactional(readOnly = true)
    public Page<BookDTO> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).map(BookDTO::new);
    }

    public Page<BookDTO> findByTitleContainingIgnoreCase(String text, Pageable pageable){
        return bookRepository.findByTitleContainingIgnoreCase(text, pageable).map(BookDTO::new);
    }

    public Page<BookDTO> findByLanguageContainingIgnoreCase(String text, Pageable pageable){
        return bookRepository.findByLanguageContainingIgnoreCase(text, pageable).map(BookDTO::new);
    }

    public Page<BookDTO> findByPublisherContainingIgnoreCase(String text, Pageable pageable){
        return bookRepository.findByPublisherContainingIgnoreCase(text, pageable).map(BookDTO::new);
    }

    public Page<BookDTO> findBooksByAuthorName(String author, Pageable pageable){
        return bookRepository.findBooksByAuthorName(author, pageable).map(BookDTO::new);
    }

    public BookDTO save(BookDTO bookDTO){
        checkRegisteredISBN(bookDTO.getIsbn());
        bookDTO.setId(null);
        if(bookDTO.getUrlCover() == null) bookDTO.setUrlCover("51264896706_e66beed079_n.jpg");
        List<Author> listAuthor = checkRegisteredAuthors(bookDTO.getAuthors());
        bookDTO.getAuthors().clear();
        Book book = fromDTO(bookDTO);
        book.getAuthors().addAll(listAuthor);
        return new BookDTO(bookRepository.save(book));
    }

    public BookDTO update(BookDTO bookDTO){
        checkRegisteredISBN(bookDTO.getIsbn());
        checkRegisteredId(bookDTO.getId());
        List<Author> listAuthor = checkRegisteredAuthors(bookDTO.getAuthors());
        Book book = fromDTO(bookDTO);
        book.getAuthors().addAll(listAuthor);
        return new BookDTO(bookRepository.save(book));
    }

    public void deleteById(Long id){
        if(checkRegisteredId(id)) bookRepository.deleteById(id);
    }

    private List<Author> fromListDTO(List<AuthorDTO> authorDTOList){
        List<Author> list = new ArrayList<>();
        for(AuthorDTO authorDTO : authorDTOList){
            Author author = new Author(
                    authorDTO.getId(),
                    authorDTO.getName(),
                    authorDTO.getBirthDate(),
                    authorDTO.getEmail(),
                    authorDTO.getPhone(),
                    authorDTO.getBiography(),
                    authorDTO.getUrlPicture()
            );
            list.add(author);
        }
        return list;
    }

    private Book fromDTO(BookDTO bookDTO) {
        Book book = new Book(
                bookDTO.getId(),
                bookDTO.getIsbn(),
                bookDTO.getTitle(),
                bookDTO.getPrintLength(),
                bookDTO.getLanguage(),
                bookDTO.getPublicationYear(),
                bookDTO.getPublisher(),
                bookDTO.getUrlCover(),
                bookDTO.getSynopsis(),
                bookDTO.getBookGenre()
        );
        return book;
    }

    private List<Author> checkRegisteredAuthors(List<AuthorDTO> list) {
        List<Author> listAuthor = new ArrayList<>();
        for(AuthorDTO authorDTO : list){
            Author author = authorRepository.findById(authorDTO.getId())
                    .orElseThrow(() -> new AuthorNotFoundException(authorDTO.getId()));
            listAuthor.add(author);
        }
        return listAuthor;
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
