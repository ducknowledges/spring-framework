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
import org.springframework.data.domain.PageRequest;

@DataJpaTest
@DisplayName("Class BookDaoJpa")
class BookDaoJpaTest {

    private static final long FIRST_BOOK_ID = 1L;
    private static final int BOOK_ENTRIES_SIZE = 3;
    private static final int EXPECTED_QUERIES_COUNT = 2;

    @Autowired
    private TestEntityManager manager;

    @Autowired
    private BookDao bookDao;

    @DisplayName("should return books without n + 1 request")
    @Test
    void shouldReturnCorrectBooksWithAllInfo() {
        SessionFactory sessionFactory = manager.getEntityManager().getEntityManagerFactory()
            .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        List<Book> actualBooks = bookDao.findAll(PageRequest.of(0, BOOK_ENTRIES_SIZE))
            .getContent();

        assertThat(actualBooks).isNotNull().hasSize(BOOK_ENTRIES_SIZE)
            .allMatch(b -> !b.getName().isEmpty())
            .allMatch(b -> b.getAuthor() != null && !b.getAuthor().getName().isEmpty())
            .allMatch(b -> b.getGenre() != null && !b.getGenre().getName().isEmpty());

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount())
            .isEqualTo(EXPECTED_QUERIES_COUNT);

        sessionFactory.getStatistics().clear();
        sessionFactory.getStatistics().setStatisticsEnabled(false);
    }

    @DisplayName("should delete book by id with orphan comments")
    @Test
    void shouldDeleteBookByIdWithChildComments() {
        List<BookComment> orphanCommentsBefore = manager.getEntityManager().createQuery(
            "select c from BookComment c where c.book.id = :bookId", BookComment.class)
            .setParameter("bookId", FIRST_BOOK_ID).getResultList();
        assertThat(orphanCommentsBefore).hasSize(2);

        bookDao.deleteById(FIRST_BOOK_ID);
        Book actualBook = manager.find(Book.class, FIRST_BOOK_ID);
        assertThat(actualBook).isNull();

        List<BookComment> orphanCommentsAfter = manager.getEntityManager().createQuery(
                "select c from BookComment c where c.book.id = :bookId", BookComment.class)
            .setParameter("bookId", FIRST_BOOK_ID).getResultList();
        assertThat(orphanCommentsAfter).isEmpty();
    }
}