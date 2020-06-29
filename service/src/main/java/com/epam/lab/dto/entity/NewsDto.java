package com.epam.lab.dto.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

public class NewsDto implements AbstractDto {

    private Long id;
    @NotNull(message = "author cant be null")
    @Valid
    private AuthorDto authorDto;
    @NotNull(message = "tags cant be null")
    @Valid
    private Set<TagDto> tagDto;
    @NotNull(message = "Please provide a title")
    @Pattern(regexp = "[A-Za-z]{3,30}", message = "title should be less than 30 characters")
    private String title;
    @NotNull(message = "Please provide a short text")
    @Pattern(regexp = "[A-Za-z]{3,100}", message = "short text should be less than 100 characters")
    private String shortText;
    @NotNull(message = "Please provide a long text")
    @Pattern(regexp = "[A-Za-z]{3,2000}", message = "long text should be less than 2000 characters")
    private String longText;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate modificationDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public String getLongText() {
        return longText;
    }

    public void setLongText(String longText) {
        this.longText = longText;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDate modificationDate) {
        this.modificationDate = modificationDate;
    }

    public AuthorDto getAuthorDto() {
        return authorDto;
    }

    public void setAuthorDto(AuthorDto authorDto) {
        this.authorDto = authorDto;
    }

    public Set<TagDto> getTagDto() {
        return tagDto;
    }

    public void setTagDto(Set<TagDto> tagDto) {
        this.tagDto = tagDto;
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
        NewsDto newsDto = (NewsDto) o;
        return Objects.equals(id, newsDto.id) &&
                Objects.equals(authorDto, newsDto.authorDto) &&
                Objects.equals(tagDto, newsDto.tagDto) &&
                Objects.equals(title, newsDto.title) &&
                Objects.equals(shortText, newsDto.shortText) &&
                Objects.equals(longText, newsDto.longText) &&
                Objects.equals(date, newsDto.date) &&
                Objects.equals(modificationDate, newsDto.modificationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authorDto, tagDto, title, shortText, longText, date, modificationDate);
    }

    @Override
    public String toString() {
        return "NewsDto{" +
                "id=" + id +
                ", authorDto=" + authorDto +
                ", tagDto=" + tagDto +
                ", title='" + title + '\'' +
                ", shortText='" + shortText + '\'' +
                ", longText='" + longText + '\'' +
                ", date=" + date +
                ", modificationDate=" + modificationDate +
                '}';
    }
}
