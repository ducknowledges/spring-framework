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

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "id", nullable = false)
    private Book book;

    public BookComment() {
    }

    public BookComment(String content, Book book) {
        this.content = Objects.requireNonNull(content);
        this.book = Objects.requireNonNull(book);
    }

    public BookComment(Long id, String content, Book book) {
        this.id = Objects.requireNonNull(id);
        this.content = Objects.requireNonNull(content);
        this.book = Objects.requireNonNull(book);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = Objects.requireNonNull(id);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = Objects.requireNonNull(content);
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
            && content.equals(that.content)
            && book.equals(that.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, book);
    }
}
