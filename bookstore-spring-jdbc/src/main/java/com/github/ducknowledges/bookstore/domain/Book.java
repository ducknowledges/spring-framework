package com.github.ducknowledges.bookstore.domain;

import java.util.Objects;

public class Book {

    private final Long id;
    private final String name;
    private final Author author;
    private final Genre genre;

    public Book(String name, Author author, Genre genre) {
        this.id = null;
        this.name = Objects.requireNonNull(name);
        this.author = Objects.requireNonNull(author);
        this.genre = Objects.requireNonNull(genre);
    }

    public Book(Long id, String name, Author author, Genre genre) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.author = Objects.requireNonNull(author);
        this.genre = Objects.requireNonNull(genre);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Author getAuthor() {
        return author;
    }

    public Genre getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return "Book{"
            + "id=" + id
            + ", name='" + name + '\''
            + ", author=" + author
            + ", genre=" + genre
            + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        return name.equals(book.name)
            && author.equals(book.author)
            && genre.equals(book.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, author, genre);
    }
}
