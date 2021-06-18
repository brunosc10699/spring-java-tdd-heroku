package com.bruno.project.services;

import com.bruno.project.dto.BookDTO;
import com.bruno.project.entities.Book;
import com.bruno.project.repositories.BookRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    private Book expectedBook = new Book(
            1,
            "978-0099520320",
            "The Bat",
            432,
            "English",
            "1997",
            "Harvill Secker",
            null,
            6,
            "Jo Nesbø"
    );

    private BookDTO givenBook = new BookDTO(expectedBook);

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;
}
