package com.github.ducknowledges.bookstore.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.domain.BookComment;
import java.util.List;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@DisplayName("Class BookCommentDaoJpa")
class BookCommentDaoJpaTest {

    private static final long FIRST_BOOK_COMMENT_ID = 1L;
    private static final long BOOK_ID = 1L;
    private static final long BOOK_COMMENT_ENTRIES_SIZE = 4;
    private static final int EXPECTED_QUERIES_COUNT = 1;

    @Autowired
    private TestEntityManager manager;

    @Autowired
    private BookCommentDao bookCommentDao;

    @Test
    @DisplayName("should create book comment")
    void shouldCreateBook() {
        Book book = new Book();
        book.setId(BOOK_ID);
        BookComment bookComment = new BookComment("new comment", book);

        BookComment actualComment = bookCommentDao.save(bookComment);
        assertThat(actualComment.getId()).isPositive().isEqualTo(BOOK_COMMENT_ENTRIES_SIZE + 1);
    }

    @Test
    @DisplayName("should return all Comments")
    void shouldReadAllComments() {
        List<BookComment> expectedBooks = manager.getEntityManager()
            .createQuery(
                "select b from BookComment b where b.id >= :fromId and b.id <= :toId",
                BookComment.class)
            .setParameter("fromId", FIRST_BOOK_COMMENT_ID)
            .setParameter("toId", BOOK_COMMENT_ENTRIES_SIZE - 1)
            .getResultList();
        List<BookComment> actualBooks = bookCommentDao
            .findAllByIdGreaterThanEqualAndIdLessThanEqual(
                FIRST_BOOK_COMMENT_ID, BOOK_COMMENT_ENTRIES_SIZE - 1
        );
        assertThat(actualBooks).hasSize((int) BOOK_COMMENT_ENTRIES_SIZE - 1)
            .usingRecursiveComparison().isEqualTo(expectedBooks);
    }

    @Test
    @DisplayName("should return empty comments")
    void shouldReadEmptyComments() {
        List<BookComment> actualComments = bookCommentDao
            .findAllByIdGreaterThanEqualAndIdLessThanEqual(
                BOOK_COMMENT_ENTRIES_SIZE + 1, BOOK_COMMENT_ENTRIES_SIZE + 2);
        assertThat(actualComments).isEmpty();
    }

    @DisplayName("should return list of comments without n + 1 request")
    @Test
    void shouldReturnCorrectCommentsWithAllInfo() {
        SessionFactory sessionFactory = manager.getEntityManager().getEntityManagerFactory()
            .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        List<BookComment> actualComments = bookCommentDao
            .findAllByIdGreaterThanEqualAndIdLessThanEqual(
                FIRST_BOOK_COMMENT_ID,
                BOOK_COMMENT_ENTRIES_SIZE
            );

        assertThat(actualComments).isNotNull().hasSize((int) BOOK_COMMENT_ENTRIES_SIZE)
            .allMatch(c -> !c.getContent().isEmpty())
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

    @DisplayName("should delete comment without book parent")
    @Test
    void shouldDeleteComment() {
        long parentBookId = manager.find(BookComment.class, FIRST_BOOK_COMMENT_ID)
            .getBook()
            .getId();

        bookCommentDao.deleteById(FIRST_BOOK_COMMENT_ID);
        BookComment actualComment = manager.find(BookComment.class, FIRST_BOOK_COMMENT_ID);
        assertThat(actualComment).isNull();

        Book actualBook = manager.find(Book.class, parentBookId);
        assertThat(actualBook).isNotNull();
    }
}