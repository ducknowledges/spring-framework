package com.github.ducknowledges.bookstore.dto;

import com.github.ducknowledges.bookstore.domain.Book;
import java.util.Objects;
import javax.validation.constraints.Size;

public class BookDto {
    private Long id;

    @Size(min = 2, max = 255, message = "Should has length 2-255")
    private String name;

    private Long authorId;

    private Long genreId;

    public BookDto() {

    }

    public BookDto(Book book) {
        this.id = book.getId();
        this.name = book.getName();
        this.authorId = book.getAuthor().getId();
        this.genreId = book.getGenre().getId();
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

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public long getGenreId() {
        return genreId;
    }

    public void setGenreId(long genreId) {
        this.genreId = genreId;
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
