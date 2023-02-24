package com.github.ducknowledges.bookstore.dto;

import com.github.ducknowledges.bookstore.domain.Book;
import java.util.Objects;
import javax.validation.constraints.Size;

public class BookDto {
    private Long id;

    @Size(min = 2, max = 255, message = "Should has length 2-255")
    private String name;

    private Long authorId;

    private String author;

    private Long genreId;

    private String genre;

    public BookDto() {

    }

    public BookDto(Book book) {
        this.id = book.getId();
        this.name = book.getName();
        this.authorId = book.getAuthor().getId();
        this.author = book.getAuthor().getName();
        this.genreId = book.getGenre().getId();
        this.genre = book.getGenre().getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BookDto bookDto = (BookDto) o;
        return Objects.equals(id, bookDto.id)
            && Objects.equals(authorId, bookDto.authorId)
            && Objects.equals(genreId, bookDto.genreId)
            && Objects.equals(name, bookDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, authorId, genreId);
    }
}
