package com.bruno.project.repositories;

import com.bruno.project.entities.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Page<Author> findByNameIgnoreCase(String name, Pageable pageable);

    Optional<Author> findByEmailIgnoreCase(String email);
}
