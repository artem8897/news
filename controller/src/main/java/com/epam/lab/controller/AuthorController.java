package com.epam.lab.controller;


import com.epam.lab.dto.entity.AuthorDto;
import com.epam.lab.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("authors")
@Validated
public class AuthorController {

    private AuthorService authorService;
    private static final String REG_EX_NAME = "[A-Za-z]{3,30}";

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping()
    public ResponseEntity<List<AuthorDto>> findAuthorByName(@RequestParam(required = false) @Pattern(regexp = REG_EX_NAME, message = ControllerMessage.WRONG_NAME) String name,
                                                            @RequestParam(required = false) @Pattern(regexp = REG_EX_NAME, message = ControllerMessage.WRONG_SURNAME) String surname) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setName(name);
        authorDto.setSurname(surname);
        List<AuthorDto> authorDtoList = authorService.findByName(authorDto);
        return ResponseEntity.ok(authorDtoList);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDto> findAuthorById(@PathVariable @NotNull(message = ControllerMessage.ID_NULL_MESSAGE) @Min(value = 1, message = ControllerMessage.WRONG_ID_MESSAGE) Long id) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(id);
        authorDto = authorService.findById(authorDto);
        return ResponseEntity.ok(authorDto);
    }

    @PostMapping(headers = {"Content-type=application/json"})
    public ResponseEntity<AuthorDto> create(@Valid @RequestBody AuthorDto authorDto) {
        authorDto = authorService.add(authorDto);
        return ResponseEntity.ok(authorDto);
    }

    @PutMapping(value = "/{id}", headers = {"Content-type=application/json"})
    public ResponseEntity<AuthorDto> update(@RequestBody @Valid AuthorDto authorDto,
                                            @PathVariable @NotNull(message = ControllerMessage.ID_NULL_MESSAGE)
                                            @Min(value = 1, message = ControllerMessage.WRONG_ID_MESSAGE) Long id) {
        authorDto.setId(id);
        authorDto = authorService.update(authorDto);
        return ResponseEntity.ok(authorDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @NotNull(message = ControllerMessage.ID_NULL_MESSAGE) @Min(value = 1, message = ControllerMessage.WRONG_ID_MESSAGE) Long id) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(id);
        authorService.delete(authorDto);
        return ResponseEntity.ok("deleted author with id = " + id);
    }

    @GetMapping(value = "/count")
    public long getCountOFNews() {
        return authorService.findCount();
    }
}