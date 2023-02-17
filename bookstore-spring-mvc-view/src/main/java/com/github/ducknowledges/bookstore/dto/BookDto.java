package com.github.ducknowledges.bookstore.dto;

import static java.util.Objects.isNull;

import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.domain.Genre;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BookDto {
    private long id;

    @Size(min = 2, max = 255, message = "Should has length 2-255")
    private String name;

    private Long authorId;

    private Long genreId;

    public BookDto() {

    }

    public BookDto(String name, long authorId, long genreId) {
        this.name = name;
        this.authorId = authorId;
        this.genreId = genreId;
    }

    public BookDto(Long id, String name, long authorId, long genreId) {
        this.id = id;
        this.name = name;
        this.authorId = authorId;
        this.genreId = genreId;
    }

    public Book toDomainObject() {
        Author author = new Author();
        author.setId(authorId);
        Genre genre = new Genre();
        genre.setId(genreId);
        return id == 0
            ? new Book(name, author, genre) :
            new Book(id, name, author, genre);
    }

    public static BookDto toDto(Book book) {
        return new BookDto(
            book.getId(),
            book.getName(),
            book.getAuthor().getId(),
            book.getGenre().getId()
        );
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
        return id == bookDto.id
            && authorId == bookDto.authorId
            && genreId == bookDto.genreId
            && Objects.equals(name, bookDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, authorId, genreId);
    }
}
