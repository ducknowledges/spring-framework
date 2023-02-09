package com.github.ducknowledges.bookstore.service;

import com.github.ducknowledges.bookstore.domain.BookComment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface BookCommentService {
    BookComment createComment(BookComment comment);

    Optional<BookComment> getComment(long id);

    Page<BookComment> getComments(int page, int size);

    Page<BookComment> getCommentsByBookId(long bookId, int page, int size);

    BookComment update(BookComment comment);

    void deleteById(long id);
}
