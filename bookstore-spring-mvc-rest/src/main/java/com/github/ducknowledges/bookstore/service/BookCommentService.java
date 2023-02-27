package com.github.ducknowledges.bookstore.service;

import com.github.ducknowledges.bookstore.domain.BookComment;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookCommentService {
    BookComment createComment(BookComment comment);

    Optional<BookComment> getComment(long id);

    Page<BookComment> getComments(Pageable pageable);

    Page<BookComment> getCommentsByBookId(long bookId, Pageable pageable);

    BookComment update(BookComment comment);

    void deleteById(long id);
}
