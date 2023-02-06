package com.github.ducknowledges.bookstore.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.domain.BookComment;
import java.util.List;
import java.util.Optional;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(BookCommentDaoJpa.class)
@DisplayName("Class BookCommentDaoJpa")
class BookCommentDaoJpaTest {

    private static final long FIRST_BOOK_COMMENT_ID = 1L;
    private static final long BOOK_ID = 1L;
    private static final int BOOK_COMMENT_ENTRIES_SIZE = 4;
    private static final int EXPECTED_QUERIES_COUNT = 4;

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

        BookComment actualComment = bookCommentDao.create(bookComment);
        assertThat(actualComment.getId()).isPositive().isEqualTo(BOOK_COMMENT_ENTRIES_SIZE + 1);

        BookComment expectedBookComment = manager.find(BookComment.class, actualComment.getId());
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(expectedBookComment);
    }

    @Test
    @DisplayName("should return expected book comment by id")
    void shouldReadExpectedBookCommentById() {
        Optional<BookComment> expectedComment = Optional.of(
            manager.find(BookComment.class, FIRST_BOOK_COMMENT_ID)
        );
        Optional<BookComment> actualComment = bookCommentDao.readById(FIRST_BOOK_COMMENT_ID);
        assertThat(actualComment)
            .usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @Test
    @DisplayName("should return empty book by id if book does not exist")
    void shouldReturnEmptyBook() {
        Optional<BookComment> actualBook = bookCommentDao.readById(BOOK_COMMENT_ENTRIES_SIZE + 1);
        assertThat(actualBook).isEmpty();
    }

    @Test
    @DisplayName("should return all Comments")
    void shouldReadAllComments() {
        List<BookComment> expectedBooks = manager.getEntityManager().createQuery(
                "select c from BookComment c where c.id >= :fromId and c.id <= :toId",
                BookComment.class)
            .setParameter("fromId", FIRST_BOOK_COMMENT_ID)
            .setParameter("toId", FIRST_BOOK_COMMENT_ID + 1)
            .getResultList();
        List<BookComment> actualBooks = bookCommentDao.readAll(
            FIRST_BOOK_COMMENT_ID,
            FIRST_BOOK_COMMENT_ID + 1
        );
        assertThat(actualBooks).hasSize(BOOK_COMMENT_ENTRIES_SIZE - 2)
            .usingRecursiveComparison().isEqualTo(expectedBooks);
    }

    @Test
    @DisplayName("should return empty comments")
    void shouldReadEmptyComments() {
        List<BookComment> actualComments = bookCommentDao.readAll(
            BOOK_COMMENT_ENTRIES_SIZE + 1,
            BOOK_COMMENT_ENTRIES_SIZE + 2
        );
        assertThat(actualComments).isEmpty();
    }

    @DisplayName("should return list of comments without n + 1 request")
    @Test
    void shouldReturnCorrectCommentsWithAllInfo() {
        SessionFactory sessionFactory = manager.getEntityManager().getEntityManagerFactory()
            .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        List<BookComment> actualComments = bookCommentDao.readAll(
            FIRST_BOOK_COMMENT_ID,
            BOOK_COMMENT_ENTRIES_SIZE);

        assertThat(actualComments).isNotNull().hasSize(BOOK_COMMENT_ENTRIES_SIZE)
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

    @DisplayName("should update comment content by id")
    @Test
    void shouldUpdateComment() {
        BookComment comment = manager.find(BookComment.class, FIRST_BOOK_COMMENT_ID);
        Book book = new Book();
        book.setId(BOOK_ID + 1);
        BookComment newComment = new BookComment(comment.getId(), "new content", book);
        BookComment actualComment = bookCommentDao.update(newComment);
        BookComment expectedComment = manager.find(BookComment.class, FIRST_BOOK_COMMENT_ID);

        assertThat(actualComment).usingRecursiveComparison().isEqualTo(expectedComment);

    }

    @DisplayName("should update comment content by id")
    @Test
    void shouldUpdateContentById() {
        BookComment comment = manager.find(BookComment.class, FIRST_BOOK_COMMENT_ID);
        BookComment actualComment = new BookComment(
            comment.getId(),
            "new comment",
            comment.getBook()
        );
        bookCommentDao.update(actualComment);
        BookComment expectedComment = manager.find(BookComment.class, FIRST_BOOK_COMMENT_ID);
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(expectedComment);

    }

    @DisplayName("should delete comment")
    @Test
    void shouldDeleteComment() {
        bookCommentDao.delete(FIRST_BOOK_COMMENT_ID);
        BookComment actualComment = manager.find(BookComment.class, FIRST_BOOK_COMMENT_ID);
        assertThat(actualComment).isNull();
    }
}