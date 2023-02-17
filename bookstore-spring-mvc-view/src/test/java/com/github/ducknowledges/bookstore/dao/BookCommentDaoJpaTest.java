package com.github.ducknowledges.bookstore.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ducknowledges.bookstore.domain.BookComment;
import java.util.List;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;

@DataJpaTest
@DisplayName("Class BookCommentDaoJpa")
class BookCommentDaoJpaTest {

    private static final long FIRST_BOOK_COMMENT_ID = 1L;
    private static final long FIRST_BOOK_ID = 1L;
    private static final int BOOK_ENTRIES_SIZE = 3;
    private static final int BOOK_COMMENT_ENTRIES_SIZE = 4;
    private static final int EXPECTED_QUERIES_COUNT = 1;

    @Autowired
    private TestEntityManager manager;

    @Autowired
    private BookCommentDao bookCommentDao;

    @Test
    @DisplayName("should return all comments by book id")
    void shouldReadAllComments() {
        List<BookComment> expectedComments = manager.getEntityManager()
            .createQuery(
                "select c from BookComment c where c.book.id = :bookId",
                BookComment.class)
            .setParameter("bookId", FIRST_BOOK_COMMENT_ID)
            .getResultList();
        List<BookComment> actualComments = bookCommentDao
            .findAllByBookId(FIRST_BOOK_ID, PageRequest.of(0, BOOK_COMMENT_ENTRIES_SIZE))
            .getContent();
        assertThat(actualComments).hasSize(2)
            .usingRecursiveComparison().isEqualTo(expectedComments);
    }

    @Test
    @DisplayName("should return empty comments by book id")
    void shouldReadEmptyComments() {
        List<BookComment> actualComments = bookCommentDao
            .findAllByBookId(
                BOOK_ENTRIES_SIZE + 1,
                PageRequest.of(0, BOOK_COMMENT_ENTRIES_SIZE)
            ).getContent();
        assertThat(actualComments).isEmpty();
    }


    @DisplayName("should return comments by id without n + 1 request")
    @Test
    void shouldReturnCorrectCommentsByIdWithAllInfo() {
        SessionFactory sessionFactory = manager.getEntityManager().getEntityManagerFactory()
            .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        List<BookComment> actualComments = bookCommentDao.findAllByBookId(
                FIRST_BOOK_ID,
                PageRequest.of(0, BOOK_COMMENT_ENTRIES_SIZE)
            ).getContent();

        assertThat(actualComments).isNotNull().hasSize(2)
            .allMatch(c -> !c.getMessage().isEmpty())
            .allMatch(c -> c.getBook() != null
                && c.getBook().getId() != null
                && !c.getBook().getName().isEmpty()
                && c.getBook().getAuthor() != null
                && c.getBook().getGenre() != null);

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount())
            .isEqualTo(EXPECTED_QUERIES_COUNT);
        sessionFactory.getStatistics().clear();
        sessionFactory.getStatistics().setStatisticsEnabled(false);
    }

    @DisplayName("should return comments by id without n + 1 request")
    @Test
    void shouldReturnCorrectCommentsWithAllInfo() {
        SessionFactory sessionFactory = manager.getEntityManager().getEntityManagerFactory()
            .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        List<BookComment> actualComments = bookCommentDao.findAll(
            PageRequest.of(0, BOOK_COMMENT_ENTRIES_SIZE)
        ).getContent();

        assertThat(actualComments).isNotNull().hasSize(BOOK_COMMENT_ENTRIES_SIZE)
            .allMatch(c -> !c.getMessage().isEmpty())
            .allMatch(c -> c.getBook() != null
                && c.getBook().getId() != null
                && !c.getBook().getName().isEmpty()
                && c.getBook().getAuthor() != null
                && c.getBook().getGenre() != null);

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount())
            .isEqualTo(EXPECTED_QUERIES_COUNT + 1);
        sessionFactory.getStatistics().clear();
        sessionFactory.getStatistics().setStatisticsEnabled(false);
    }

}