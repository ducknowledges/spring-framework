package com.github.ducknowledges.bookstore.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ducknowledges.bookstore.dao.BookCommentDaoJpa;
import com.github.ducknowledges.bookstore.dao.BookDaoJpa;
import com.github.ducknowledges.bookstore.domain.BookComment;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({BookDaoJpa.class, BookCommentServiceImpl.class, BookCommentDaoJpa.class})
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

        List<BookComment> actualComments = commentService.getCommentsByBookId(FIRST_BOOK_ID);
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
        List<BookComment> actualComments = commentService.getCommentsByBookId(
            BOOK_ENTRIES_SIZE + 1);
        assertThat(actualComments).isEqualTo(expectedComments);
    }
}