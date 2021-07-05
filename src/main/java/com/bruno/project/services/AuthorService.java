package com.bruno.project.services;

import com.bruno.project.dto.AuthorDTO;
import com.bruno.project.entities.Author;
import com.bruno.project.repositories.AuthorRepository;
import com.bruno.project.services.exceptions.AuthorEmailAlreadyRegisteredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
        checkGivenId(id);
        checkRegisteredEmail(authorDTO.getId(), authorDTO.getEmail());
        Author author = fromDTO(authorDTO);
        return new AuthorDTO(authorRepository.save(author));
    }

    public void deleteById(Long id) {
        checkGivenId(id);
        authorRepository.deleteById(id);
    }

    private Author fromDTO(AuthorDTO authorDTO) {
        return new Author(
                authorDTO.getId(),
                authorDTO.getName(),
                authorDTO.getBirthDate(),
                authorDTO.getEmail(),
                authorDTO.getPhone(),
                authorDTO.getBiography(),
                authorDTO.getUrlPicture()
        );
    }

    private void checkGivenId(Long id) {
        authorRepository.getById(id);
    }

    private void checkRegisteredEmail(Long id, String email){
        Optional<Author> author = authorRepository.findByEmailIgnoreCase(email);
        if(author.isPresent() && author.get().getId() != id) throw new AuthorEmailAlreadyRegisteredException(email);
    }
}
