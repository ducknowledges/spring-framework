package com.github.ducknowledges.bookstore.dto;

import com.github.ducknowledges.bookstore.domain.BookComment;
import java.util.Objects;

public class BookCommentDto {

    private final Long id;

    private final String message;

    private final String bookName;

    public BookCommentDto(BookComment bookComment) {
        this.id = bookComment.getId();
        this.message = bookComment.getMessage();
        this.bookName = bookComment.getBook().getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BookCommentDto that = (BookCommentDto) o;
        return Objects.equals(id, that.id)
            && Objects.equals(message, that.message)
            && Objects.equals(bookName, that.bookName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, bookName);
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getBookName() {
        return bookName;
    }
}
