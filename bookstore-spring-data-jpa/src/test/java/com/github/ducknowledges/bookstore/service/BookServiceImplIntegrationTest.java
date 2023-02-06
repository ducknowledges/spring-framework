package com.github.ducknowledges.bookstore.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.domain.BookComment;
import com.github.ducknowledges.bookstore.domain.Genre;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({BookServiceImpl.class, BookCommentServiceImpl.class})
@DisplayName("Class BookServiceImplIntegrationTest")
class BookServiceImplIntegrationTest {

    private static final long FIRST_BOOK_ID = 1L;
    private static final long BOOK_ENTRIES_SIZE = 3;
    private static final long FIRST_AUTHOR_ID = 1L;
    private static final long FIRST_GENRE_ID = 1L;

    @Autowired
    private TestEntityManager manager;

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private BookCommentService commentService;

    @Test
    @DisplayName("should create book")
    void shouldCreateBook() {
        Author author = manager.find(Author.class, FIRST_AUTHOR_ID);
        Genre genre = manager.find(Genre.class, FIRST_GENRE_ID);
        Book newBook = new Book("book4", author, genre);

        Book actualBook = bookService.createBook(newBook);
        Book expectedBook = manager.find(Book.class, BOOK_ENTRIES_SIZE + 1);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("should return book by id")
    void shouldReturnBookById() {
        Book book = manager.find(Book.class, FIRST_BOOK_ID);
        Optional<Book> expectedBook = Optional.ofNullable(book);
        Optional<Book> actualBook = bookService.getBook(FIRST_BOOK_ID);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("should return empty book by id")
    void shouldReturnEmptyBookById() {
        Book book = manager.find(Book.class, BOOK_ENTRIES_SIZE + 1);
        Optional<Book> expectedBook = Optional.ofNullable(book);
        Optional<Book> actualBook = bookService.getBook(BOOK_ENTRIES_SIZE + 1);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("should return all books")
    void shouldReturnAllBooks() {
        List<Book> expectedBooks = manager.getEntityManager().createQuery(
                "select b from Book b join fetch b.author join fetch b.genre "
                    + "where b.id >= :fromId and b.id <= :toId",
                Book.class)
            .setParameter("fromId", FIRST_BOOK_ID)
            .setParameter("toId", FIRST_BOOK_ID + 1)
            .getResultList();

        List<Book> actualBooks = bookService.getBooks(FIRST_BOOK_ID, FIRST_BOOK_ID + 1);
        assertThat(actualBooks)
            .usingRecursiveComparison().isEqualTo(expectedBooks);
    }

    @Test
    @DisplayName("should update book")
    void shouldUpdateBook() {
        Book book = manager.find(Book.class, FIRST_GENRE_ID);
        bookService.update(book);
        Optional<Book> expectedBook = Optional.of(book);
        Optional<Book> actualBook = bookService.getBook(book.getId());
        assertThat(actualBook)
            .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("should delete book with orphan comments")
    void shouldDeleteBookWithOrphanComments() {
        Optional<Book> bookBefore = bookService.getBook(FIRST_BOOK_ID);
        assertThat(bookBefore).isPresent();

        List<BookComment> orphanCommentsBefore = commentService.getCommentsByBookId(FIRST_BOOK_ID);
        assertThat(orphanCommentsBefore).hasSize(2);

        bookService.deleteById(FIRST_BOOK_ID);
        Optional<Book> bookAfter = bookService.getBook(FIRST_BOOK_ID);
        assertThat(bookAfter).isEmpty();

        List<BookComment> orphanCommentsAfter = commentService.getCommentsByBookId(FIRST_BOOK_ID);
        assertThat(orphanCommentsAfter).isEmpty();
    }
}