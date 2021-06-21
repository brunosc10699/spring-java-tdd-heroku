package com.bruno.project.services;

import com.bruno.project.dto.AuthorDTO;
import com.bruno.project.entities.Author;
import com.bruno.project.repositories.AuthorRepository;
import com.bruno.project.services.exceptions.AuthorEmailAlreadyRegisteredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Transactional(readOnly = true)
    public Page<AuthorDTO> findAll(Pageable pageable){
        return authorRepository.findAll(pageable).map(AuthorDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<AuthorDTO> findByNameIgnoreCase(String name, PageRequest pageRequest) {
        return authorRepository.findByNameIgnoreCase(name, pageRequest).map(AuthorDTO::new);
    }

    public AuthorDTO save(AuthorDTO authorDTO) {
        checkRegisteredEmail(authorDTO.getEmail());
        return new AuthorDTO(authorRepository.save(new Author(authorDTO)));
    }

    private void checkRegisteredEmail(String email){
        Optional<Author> author = authorRepository.findByEmailIgnoreCase(email);
        if(author.isPresent()) throw new AuthorEmailAlreadyRegisteredException(email);
    }
}
