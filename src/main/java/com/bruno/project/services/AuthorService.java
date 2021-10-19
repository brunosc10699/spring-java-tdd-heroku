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

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional(readOnly = true)
    public Page<AuthorDTO> findAll(Pageable pageable){
        return authorRepository.findAll(pageable).map(AuthorDTO::new);
    }

    public Page<AuthorDTO> findByNameContainingIgnoreCase(String name, Pageable pageable) {
        return authorRepository.findByNameContainingIgnoreCase(name, pageable).map(AuthorDTO::new);
    }

    public AuthorDTO save(AuthorDTO authorDTO) {
        checkRegisteredEmail(authorDTO.getId(), authorDTO.getEmail());
        authorDTO.setId(null);
        if(authorDTO.getUrlPicture() == null) authorDTO.setUrlPicture("51265117593_c76eb4ccb8_n.jpg");
        Author author = fromDTO(authorDTO);
        return new AuthorDTO(authorRepository.save(author));
    }

    public AuthorDTO updateById(Long id, AuthorDTO authorDTO) {
        try {
            checkGivenId(id);
        } catch (EntityNotFoundException e) {
            throw new AuthorNotFoundException(e.getMessage());
        }
        checkRegisteredEmail(id, authorDTO.getEmail());
        if(authorDTO.getUrlPicture() == null) authorDTO.setUrlPicture("51265117593_c76eb4ccb8_n.jpg");
        authorDTO.setId(id);
        Author author = fromDTO(authorDTO);
        return new AuthorDTO(authorRepository.save(author));
    }

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
        Author author = authorRepository.getById(id);
        return author;
    }

    private Optional<Author> checkRegisteredEmail(Long id, String email){
        Optional<Author> author = authorRepository.findByEmailIgnoreCase(email);
        if(author.isPresent() && author.get().getId() != id)
            throw new AuthorEmailAlreadyRegisteredException("The email " + email
                    + " you supplied is already registered! Try with another one.");
        return author;
    }
}
