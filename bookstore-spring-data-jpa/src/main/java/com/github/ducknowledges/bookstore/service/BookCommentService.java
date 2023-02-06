package com.github.ducknowledges.bookstore.service;

import com.github.ducknowledges.bookstore.domain.BookComment;
import java.util.List;
import java.util.Optional;

public interface BookCommentService {
    BookComment createComment(BookComment comment);

    Optional<BookComment> getComment(long id);

    List<BookComment> getComments(long fromId, long toId);

    List<BookComment> getCommentsByBookId(long bookId);

    BookComment update(BookComment comment);

    void deleteById(long id);
}
