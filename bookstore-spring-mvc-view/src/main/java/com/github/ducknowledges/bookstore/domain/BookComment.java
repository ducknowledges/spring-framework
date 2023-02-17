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
@Table(name = "book_comment")
public class BookComment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message", nullable = false)
    private String message;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "book_id",
        referencedColumnName = "id",
        nullable = false,
        updatable = false)
    private Book book;

    public BookComment() {
    }

    public BookComment(String message, Book book) {
        this.message = Objects.requireNonNull(message);
        this.book = Objects.requireNonNull(book);
    }

    public BookComment(Long id, String message, Book book) {
        this.id = Objects.requireNonNull(id);
        this.message = Objects.requireNonNull(message);
        this.book = Objects.requireNonNull(book);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = Objects.requireNonNull(id);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = Objects.requireNonNull(message);
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = Objects.requireNonNull(book);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BookComment that = (BookComment) o;
        return Objects.equals(id, that.id)
            && message.equals(that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message);
    }
}
