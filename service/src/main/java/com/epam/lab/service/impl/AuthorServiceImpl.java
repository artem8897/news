package com.epam.lab.service.impl;

import com.epam.lab.dto.entity.AuthorDto;
import com.epam.lab.mapper.AuthorMapper;
import com.epam.lab.exception.ServiceException;
import com.epam.lab.model.Author;
import com.epam.lab.repository.EntityRepository;
import com.epam.lab.service.AuthorService;
import com.epam.lab.specification.author.AuthorByFullNameSpecification;
import com.epam.lab.specification.author.AuthorByIdSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorServiceImpl implements AuthorService {

    private EntityRepository<Author> authorRepository;
    private AuthorMapper authorMapper;

    @Autowired
    public AuthorServiceImpl(EntityRepository<Author> authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    public AuthorDto add(AuthorDto authorDto) {
        if (authorDto.getId() == null) {
            Author author = authorMapper.toEntity(authorDto);
            checkForExistingAuthor(author);
            Author addedAuthor = authorRepository.addEntity(author);
            return authorMapper.toDto(addedAuthor);
        } else {
            throw new ServiceException("add shouldn't contain id");
        }
    }

    private void checkForExistingAuthor(Author author){
        AuthorByFullNameSpecification authorByFullNameSpecification = new AuthorByFullNameSpecification(author);
        List<Author> authorList = authorRepository.query(authorByFullNameSpecification);
        if(!authorList.isEmpty()){
            throw new ServiceException("author is already exist");
        }
    }

    @Override
    public AuthorDto update(AuthorDto authorDto) {
        checkForExistingAuthorId(authorDto.getId());
        Author author = authorMapper.toEntity(authorDto);
        Author addedAuthor = authorRepository.updateEntity(author);
        return authorMapper.toDto(addedAuthor);
    }

    private void checkForExistingAuthorId(long id) {
        AuthorByIdSpecification authorByIdSpecification = new AuthorByIdSpecification(id);
        List<Author> authorList = authorRepository.query(authorByIdSpecification);
        if (authorList.isEmpty()) {
            throw new ServiceException("unexpected author id");
        }
    }

    @Override
    public void delete(AuthorDto authorDto) {
        Author author = authorMapper.toEntity(authorDto);
        authorRepository.removeEntity(author);
    }

    @Override
    public List<AuthorDto> findByName(AuthorDto authorDto) {
        Author author = authorMapper.toEntity(authorDto);
        AuthorByFullNameSpecification authorByNameSpecification = new AuthorByFullNameSpecification(author);
        List<Author> authorList = authorRepository.query(authorByNameSpecification);
        return convertToAuthorListDto(authorList);
    }

    public AuthorDto findById(AuthorDto authorDto) {
        AuthorByIdSpecification authorByIdSpecification = new AuthorByIdSpecification(authorDto.getId());
        List<Author> authorList = authorRepository.query(authorByIdSpecification);
        if(authorList.size() == 1){
            Author author = authorList.get(0);
            return authorMapper.toDto(author);
        }else{
            throw new ServiceException("no such author in database");
        }
    }

    public long findCount(){
        return authorRepository.findCount();
    }

    private List<AuthorDto> convertToAuthorListDto(List<Author> authorList){
        return authorList.stream().map(authorMapper::toDto).collect(Collectors.toList());
    }
}
