package com.github.ducknowledges.bookstore.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;

    public Book() {}

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

    public void setId(Long id) {
        this.id = Objects.requireNonNull(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = Objects.requireNonNull(author);
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = Objects.requireNonNull(genre);
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
