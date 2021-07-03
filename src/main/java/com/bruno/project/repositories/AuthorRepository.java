package com.bruno.project.repositories;

import com.bruno.project.entities.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Transactional(readOnly = true)
    Page<Author> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Transactional(readOnly = true)
    Optional<Author> findByEmailIgnoreCase(String email);
}
