package com.github.ducknowledges.bookstore.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.domain.BookComment;
import com.github.ducknowledges.bookstore.domain.Genre;
import java.util.List;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@DisplayName("Class BookDaoJpa")
class BookDaoJpaTest {

    private static final long FIRST_BOOK_ID = 1L;
    private static final long BOOK_ENTRIES_SIZE = 3;
    private static final int EXPECTED_QUERIES_COUNT = 1;

    @Autowired
    private TestEntityManager manager;

    @Autowired
    private BookDao bookDao;

    @Test
    @DisplayName("should create book")
    void shouldCreateBook() {
        Author author = new Author(1L, "author1");
        Genre genre = new Genre(1L, "genre1");
        Book book = new Book("book4", author, genre);

        Book actualBook = bookDao.save(book);
        assertThat(actualBook.getId()).isPositive().isEqualTo(BOOK_ENTRIES_SIZE + 1);
    }

    @Test
    @DisplayName("should return all books")
    void shouldReadAllBooks() {
        List<Book> expectedBooks = manager.getEntityManager()
            .createQuery("select b from Book b where b.id >= :fromId and b.id <= :toId", Book.class)
            .setParameter("fromId", FIRST_BOOK_ID)
            .setParameter("toId", BOOK_ENTRIES_SIZE - 1)
            .getResultList();
        List<Book> actualBooks = bookDao.findAllByIdGreaterThanEqualAndIdLessThanEqual(
            FIRST_BOOK_ID, BOOK_ENTRIES_SIZE - 1
        );
        assertThat(actualBooks)
            .usingRecursiveComparison().isEqualTo(expectedBooks);
    }

    @Test
    @DisplayName("should return empty books")
    void shouldReadEmptyBooks() {
        List<Book> actualBooks = bookDao.findAllByIdGreaterThanEqualAndIdLessThanEqual(
            BOOK_ENTRIES_SIZE + 1, BOOK_ENTRIES_SIZE + 2
        );
        assertThat(actualBooks).isEmpty();
    }

    @DisplayName("should return list of books without n + 1 request")
    @Test
    void shouldReturnCorrectBooksWithAllInfo() {
        SessionFactory sessionFactory = manager.getEntityManager().getEntityManagerFactory()
            .unwrap(SessionFactory.class);

        sessionFactory.getStatistics().setStatisticsEnabled(true);

        List<Book> actualBooks = bookDao.findAllByIdGreaterThanEqualAndIdLessThanEqual(
            FIRST_BOOK_ID, BOOK_ENTRIES_SIZE
        );
        assertThat(actualBooks).isNotNull().hasSize((int) BOOK_ENTRIES_SIZE)
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