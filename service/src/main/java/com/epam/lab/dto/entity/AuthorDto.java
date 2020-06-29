package com.epam.lab.dto.entity;

import javax.validation.constraints.*;
import java.util.Objects;

public class AuthorDto implements AbstractDto {

    private Long id;

    @NotNull(message = "Please provide a name")
    @Pattern(regexp = "[A-Za-z]{3,30}", message = "name should be less than 30 characters")
    private String name;

    @NotNull(message = "Please provide a surname")
    @Pattern(regexp = "[A-Za-z]{3,30}", message = "name should be less than 30 characters")

    private String surname;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorDto authorDto = (AuthorDto) o;
        return Objects.equals(id, authorDto.id) &&
                Objects.equals(name, authorDto.name) &&
                Objects.equals(surname, authorDto.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname);
    }

    @Override
    public String toString() {
        return "AuthorDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}