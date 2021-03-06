package com.bruno.project.services.impl;

import com.bruno.project.dto.AuthorDTO;
import com.bruno.project.entities.Author;
import com.bruno.project.repositories.AuthorRepository;
import com.bruno.project.services.AuthorService;
import com.bruno.project.services.exceptions.ExistingResourceException;
import com.bruno.project.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<AuthorDTO> findAll(Pageable pageable){
        return authorRepository.findAll(pageable).map(AuthorDTO::toDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<AuthorDTO> findByNameContainingIgnoreCase(String name, Pageable pageable) {
        return authorRepository.findByNameContainingIgnoreCase(name, pageable).map(AuthorDTO::toDTO);
    }

    @Override
    public AuthorDTO save(AuthorDTO authorDTO) {
        checkRegisteredEmail(null, authorDTO.getEmail());
        authorDTO.setId(null);
        if(authorDTO.getUrlPicture() == null) authorDTO.setUrlPicture("51265117593_c76eb4ccb8_n.jpg");
        Author author = fromDTO(authorDTO);
        return AuthorDTO.toDTO(authorRepository.save(author));
    }

    @Override
    public AuthorDTO updateById(Long id, AuthorDTO authorDTO) {
        checkGivenId(id);
        checkRegisteredEmail(id, authorDTO.getEmail());
        if(authorDTO.getUrlPicture() == null) authorDTO.setUrlPicture("51265117593_c76eb4ccb8_n.jpg");
        authorDTO.setId(id);
        Author author = fromDTO(authorDTO);
        return AuthorDTO.toDTO(authorRepository.save(author));
    }

    @Override
    public void deleteById(Long id) {
        checkGivenId(id);
        authorRepository.deleteById(id);
    }

    private Author fromDTO(AuthorDTO authorDTO) {
        return Author.builder()
                .id(authorDTO.getId())
                .name(authorDTO.getName())
                .birthDate(authorDTO.getBirthDate())
                .email(authorDTO.getEmail())
                .phone(authorDTO.getPhone())
                .biography(authorDTO.getBiography())
                .urlPicture(authorDTO.getUrlPicture())
                .build();
    }

    private Author checkGivenId(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The ID '" + id + "' was not found!"));
    }

    @Transactional(readOnly = true)
    private Optional<Author> checkRegisteredEmail(Long id, String email){
        Optional<Author> author = authorRepository.findByEmailIgnoreCase(email);
        if(author.isPresent() && author.get().getId() != id)
            throw new ExistingResourceException("The email " + email
                    + " you supplied is already registered! Try with another one.");
        return author;
    }
}
