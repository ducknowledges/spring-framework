package com.github.ducknowledges.bookstore.integrationtest;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ducknowledges.bookstore.domain.BookComment;
import com.github.ducknowledges.bookstore.service.BookCommentService;
import com.github.ducknowledges.bookstore.service.BookCommentServiceImpl;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;

@DataJpaTest
@Import(BookCommentServiceImpl.class)
@DisplayName("Class BookCommentServiceImplIntegrationTest")
class BookCommentServiceImplIntegrationTest {

    private static final long FIRST_BOOK_ID = 1L;
    private static final long BOOK_ENTRIES_SIZE = 3;

    @Autowired
    private TestEntityManager manager;

    @Autowired
    private BookCommentService commentService;

    @Test
    @DisplayName("should return all comments by book id")
    void shouldReturnAllCommentsByBookId() {
        List<BookComment> expectedComments = manager.getEntityManager().createQuery(
            "select c from BookComment c where c.book.id = :bookId",
                BookComment.class)
            .setParameter("bookId", FIRST_BOOK_ID)
            .getResultList();

        int page = 0;
        int size = 2;
        List<BookComment> actualComments = commentService
            .getCommentsByBookId(FIRST_BOOK_ID, PageRequest.of(page, size))
            .getContent();
        assertThat(actualComments).hasSize(2).isEqualTo(expectedComments);
    }

    @Test
    @DisplayName("should return empty comments if book id not exist")
    void shouldReturnEmptyCommentsIfBookIdNotExist() {
        List<BookComment> expectedComments = manager.getEntityManager().createQuery(
                "select c from BookComment c where c.book.id = :bookId",
                BookComment.class)
            .setParameter("bookId", BOOK_ENTRIES_SIZE + 1)
            .getResultList();

        int page = 0;
        int size = 2;
        List<BookComment> actualComments = commentService
            .getCommentsByBookId(BOOK_ENTRIES_SIZE + 1, PageRequest.of(page, size))
            .getContent();
        assertThat(actualComments).isEqualTo(expectedComments);
    }
}