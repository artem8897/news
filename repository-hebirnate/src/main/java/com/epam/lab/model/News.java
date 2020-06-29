package com.epam.lab.model;

import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@javax.persistence.Entity()
@Table(name = "news")
@Access(AccessType.FIELD)
public class News implements Entity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.REPLICATE)
    @JoinTable(name = "news_author",
            joinColumns = @JoinColumn(name = "news_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"))
    private Author author;
    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade({org.hibernate.annotations.CascadeType.REPLICATE})
    @JoinTable(name = "news_tag",
    joinColumns = @JoinColumn(name = "news_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> tags;
    @Column(name = "title")
    private String title;
    @Column(name = "short_text")
    private String shortText;
    @Column(name = "long_text")
    private String longText;
    @Column(name = "creation_date", updatable = false)
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate date;
    @Column(name = "modification_date")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate modificationDate;

    public News() {
    }

    public News(Author author, Set<Tag> tag, String title, String shortText, String longText, LocalDate date, LocalDate modificationDate) {
        this.author = author;
        this.tags = tag;
        this.title = title;
        this.shortText = shortText;
        this.longText = longText;
        this.date = date;
        this.modificationDate = modificationDate;
    }

    public News(long id, String title, String shortText, String longText, LocalDate date, LocalDate modificationDate) {
        this.id = id;
        this.title = title;
        this.shortText = shortText;
        this.longText = longText;
        this.date = date;
        this.modificationDate = modificationDate;
    }


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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Set<Tag> getTag() {
        return tags;
    }

    public void setTag(Set<Tag> tag) {
        this.tags = tag;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return id == news.id &&
                Objects.equals(author, news.author) &&
                Objects.equals(tags, news.tags) &&
                Objects.equals(title, news.title) &&
                Objects.equals(shortText, news.shortText) &&
                Objects.equals(longText, news.longText) &&
                Objects.equals(date, news.date) &&
                Objects.equals(modificationDate, news.modificationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, tags, title, shortText, longText, date, modificationDate);
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", author=" + author +
                ", tags=" + tags +
                ", title='" + title + '\'' +
                ", shortText='" + shortText + '\'' +
                ", longText='" + longText + '\'' +
                ", date=" + date +
                ", modificationDate=" + modificationDate +
                '}';
    }
}
