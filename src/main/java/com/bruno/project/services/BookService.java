package com.bruno.project.services;

import com.bruno.project.dto.BookDTO;
import com.bruno.project.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream().map(BookDTO::new).collect(Collectors.toList());
    }
}
