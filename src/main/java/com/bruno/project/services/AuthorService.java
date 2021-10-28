package com.bruno.project.services;

import com.bruno.project.dto.AuthorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorService {

    Page<AuthorDTO> findAll(Pageable pageable);

    Page<AuthorDTO> findByNameContainingIgnoreCase(String name, Pageable pageable);

    AuthorDTO save(AuthorDTO authorDTO);

    AuthorDTO updateById(Long id, AuthorDTO authorDTO);

    void deleteById(Long id);
}
