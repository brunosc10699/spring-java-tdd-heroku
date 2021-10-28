package com.bruno.project.services.impl;

import com.bruno.project.dto.AuthorDTO;
import com.bruno.project.dto.BookDTO;
import com.bruno.project.entities.Author;
import com.bruno.project.entities.Book;
import com.bruno.project.repositories.AuthorRepository;
import com.bruno.project.repositories.BookRepository;
import com.bruno.project.services.BookService;
import com.bruno.project.services.exceptions.ExistingResourceException;
import com.bruno.project.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<BookDTO> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).map(BookDTO::toDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<BookDTO> findByTitleContainingIgnoreCase(String text, Pageable pageable){
        return bookRepository.findByTitleContainingIgnoreCase(text, pageable).map(BookDTO::toDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<BookDTO> findByLanguageContainingIgnoreCase(String text, Pageable pageable){
        return bookRepository.findByLanguageContainingIgnoreCase(text, pageable).map(BookDTO::toDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<BookDTO> findByPublisherContainingIgnoreCase(String text, Pageable pageable){
        return bookRepository.findByPublisherContainingIgnoreCase(text, pageable).map(BookDTO::toDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<BookDTO> findBooksByAuthorName(String author, Pageable pageable){
        return bookRepository.findBooksByAuthorName(author, pageable).map(BookDTO::toDTO);
    }

    @Override
    public BookDTO save(BookDTO bookDTO){
        checkRegisteredISBN(null, bookDTO.getIsbn());
        bookDTO.setId(null);
        if(bookDTO.getUrlCover() == null) bookDTO.setUrlCover("51264896706_e66beed079_n.jpg");
        List<Author> listAuthor = checkRegisteredAuthors(bookDTO.getAuthors());
        bookDTO.getAuthors().clear();
        Book book = fromDTO(bookDTO);
        book.getAuthors().addAll(listAuthor);
        return BookDTO.toDTO(bookRepository.save(book));
    }

    @Override
    public BookDTO updateById(Long id, BookDTO bookDTO){
        checkGivenId(id);
        checkRegisteredISBN(id, bookDTO.getIsbn());
        List<Author> listAuthor = checkRegisteredAuthors(bookDTO.getAuthors());
        if(bookDTO.getUrlCover() == null) bookDTO.setUrlCover("51264896706_e66beed079_n.jpg");
        bookDTO.setId(id);
        Book book = fromDTO(bookDTO);
        book.getAuthors().addAll(listAuthor);
        return BookDTO.toDTO(bookRepository.save(book));
    }

    @Override
    public void deleteById(Long id){
        checkGivenId(id);
        bookRepository.deleteById(id);
    }

    private Book fromDTO(BookDTO bookDTO) {
        return Book.builder()
                .id(bookDTO.getId())
                .isbn(bookDTO.getIsbn())
                .title(bookDTO.getTitle())
                .printLength(bookDTO.getPrintLength())
                .language(bookDTO.getLanguage())
                .publicationYear(bookDTO.getPublicationYear())
                .publisher(bookDTO.getPublisher())
                .urlCover(bookDTO.getUrlCover())
                .synopsis(bookDTO.getSynopsis())
                .bookGenre(bookDTO.getBookGenre())
                .build();
    }

    @Transactional(readOnly = true)
    private List<Author> checkRegisteredAuthors(List<AuthorDTO> list) {
        List<Author> listAuthor = new ArrayList<>();
        for(AuthorDTO authorDTO : list){
            Author author = authorRepository.findById(authorDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Author not found with this ID: '" + authorDTO.getId()));
            listAuthor.add(author);
        }
        return listAuthor;
    }

    private Book checkGivenId(Long id){
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The id '" + id + "' was not found!"));
    }

    @Transactional(readOnly = true)
    private Optional<Book> checkRegisteredISBN(Long id, String isbn){
        Optional<Book> book = bookRepository.findByIsbn(isbn);
        if(book.isPresent() && book.get().getId() != id)
            throw new ExistingResourceException("The ISBN '" + isbn + "' is already registered!");
        return book;
    }
}
