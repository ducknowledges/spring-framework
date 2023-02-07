package com.github.ducknowledges.bookstore.domain;

import java.util.Objects;

public class BookComment {

    private final Long id;

    private final String content;

    private final Book book;


    public BookComment(String content, Book book) {
        this.id = null;
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

    public String getContent() {
        return content;
    }

    public Book getBook() {
        return book;
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
            && content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content);
    }
}
