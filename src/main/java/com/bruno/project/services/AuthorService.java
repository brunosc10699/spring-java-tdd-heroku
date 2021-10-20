package com.bruno.project.services;

import com.bruno.project.dto.AuthorDTO;
import com.bruno.project.entities.Author;
import com.bruno.project.repositories.AuthorRepository;
import com.bruno.project.services.exceptions.AuthorEmailAlreadyRegisteredException;
import com.bruno.project.services.exceptions.AuthorNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

public interface AuthorService {

    Page<AuthorDTO> findAll(Pageable pageable);

    Page<AuthorDTO> findByNameContainingIgnoreCase(String name, Pageable pageable);

    AuthorDTO save(AuthorDTO authorDTO);

    AuthorDTO updateById(Long id, AuthorDTO authorDTO);

    void deleteById(Long id);
}
