package com.github.ducknowledges.bookstore.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.bookstore.dao.BookCommentDao;
import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.domain.BookComment;
import com.github.ducknowledges.bookstore.domain.Genre;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@DisplayName("Class BookServiceImpl")
class BookCommentServiceImplTest {

    @MockBean
    private BookCommentDao bookCommentDao;

    @Autowired
    private BookCommentService bookCommentService;

    private static BookComment comment;

    @BeforeAll
    static void setUpAll() {
        Book book = new Book();
        comment = new BookComment(1L, "content", book);
    }

    @Test
    @DisplayName("should create book comment")
    void shouldCreateBook() {
        bookCommentService.createComment(comment);
        verify(bookCommentDao, times(1)).save(comment);
    }

    @Test
    @DisplayName("should return comment by id")
    void shouldReturnBookById() {
        long id = 1;
        Optional<BookComment> expectedComment = Optional.of(comment);
        when(bookCommentDao.readById(id)).thenReturn(expectedComment);
        Optional<BookComment> actualComment = bookCommentService.getComment(id);
        assertThat(actualComment).isEqualTo(expectedComment);
        verify(bookCommentDao, times(1)).readById(id);
    }

    @Test
    @DisplayName("should return empty comment by id")
    void shouldReturnEmptyBookById() {
        long id = 1;
        Optional<BookComment> expectedComment = Optional.empty();
        when(bookCommentDao.readById(id)).thenReturn(expectedComment);
        Optional<BookComment> actualComment = bookCommentService.getComment(id);
        assertThat(actualComment).isEqualTo(expectedComment);
        verify(bookCommentDao, times(1)).readById(id);
    }

    @Test
    @DisplayName("should return all comments")
    void shouldReturnAllComments() {
        int from = 1;
        int size = 1;
        when(bookCommentDao.readAll(from, size)).thenReturn(List.of(comment));
        List<BookComment> expectedComments = List.of(comment);
        List<BookComment> actualComments = bookCommentService.getComments(from, size);
        assertThat(actualComments).isEqualTo(expectedComments);
    }

    @Test
    @DisplayName("should return all comments by book id")
    void shouldReturnAllCommentsByBookId() {
        long bookId = 1L;
        when(bookCommentDao.readAllByBookId(bookId)).thenReturn(List.of(comment));
        List<BookComment> expectedComments = List.of(comment);
        List<BookComment> actualComments = bookCommentService.getCommentsByBookId(bookId);
        assertThat(actualComments).isEqualTo(expectedComments);
    }

    @Test
    @DisplayName("should update comment's content")
    void shouldUpdateComment() {
        comment.setContent("new content");
        when(bookCommentDao.save(comment)).thenReturn(comment);
        BookComment updatedComment = bookCommentService.update(comment);
        assertThat(updatedComment).isEqualTo(comment);
        verify(bookCommentDao, times(1)).save(comment);
    }

    @Test
    @DisplayName("should delete comment")
    void shouldDeleteComment() {
        long id = 1;
        bookCommentService.delete(id);
        verify(bookCommentDao, times(1)).delete(id);
    }
}