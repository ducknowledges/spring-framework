package com.github.ducknowledges.bookstore.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.bookstore.dao.BookCommentDao;
import com.github.ducknowledges.bookstore.dao.BookDao;
import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.domain.BookComment;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
@DisplayName("Class BookServiceImpl")
class BookCommentServiceImplTest {

    @MockBean
    private BookCommentDao bookCommentDao;

    @MockBean
    private BookDao bookDao;

    @Autowired
    private BookCommentService commentService;

    private static BookComment comment;

    @BeforeAll
    static void setUpAll() {
        Book book = new Book();
        comment = new BookComment(1L, "content", book);
    }

    @Test
    @DisplayName("should create book comment")
    void shouldCreateBook() {
        commentService.createComment(comment);
        verify(bookCommentDao, times(1)).save(comment);
    }

    @Test
    @DisplayName("should return comment by id")
    void shouldReturnBookById() {
        long id = 1;
        Optional<BookComment> expectedComment = Optional.of(comment);
        when(bookCommentDao.findById(id)).thenReturn(expectedComment);
        Optional<BookComment> actualComment = commentService.getComment(id);
        assertThat(actualComment).isEqualTo(expectedComment);
        verify(bookCommentDao, times(1)).findById(id);
    }

    @Test
    @DisplayName("should return empty comment by id")
    void shouldReturnEmptyBookById() {
        long id = 1;
        Optional<BookComment> expectedComment = Optional.empty();
        when(bookCommentDao.findById(id)).thenReturn(expectedComment);
        Optional<BookComment> actualComment = commentService.getComment(id);
        assertThat(actualComment).isEqualTo(expectedComment);
        verify(bookCommentDao, times(1)).findById(id);
    }

    @Test
    @DisplayName("should return all comments")
    void shouldReturnAllComments() {
        int page = 1;
        int size = 2;
        when(bookCommentDao.findAll(PageRequest.of(page, size)))
            .thenReturn(new PageImpl<>(List.of(comment)));
        Page<BookComment> expectedComments = new PageImpl<>(List.of(comment));
        Page<BookComment> actualComments = commentService.getComments(PageRequest.of(page, size));
        assertThat(actualComments).isEqualTo(expectedComments);
    }

    @Test
    @DisplayName("should return all comments by book id")
    void shouldReturnAllCommentsByBookId() {
        int page = 1;
        int size = 2;
        long bookId = 1L;

        Book book = mock(Book.class);
        when(book.getId()).thenReturn(bookId);
        when(bookDao.findById(bookId)).thenReturn(Optional.of(book));
        when(bookCommentDao.findAllByBookId(bookId, PageRequest.of(page, size)))
            .thenReturn(new PageImpl<>(List.of(comment)));

        Page<BookComment> expectedComments = new PageImpl<>(List.of(comment));
        Page<BookComment> actualComments = commentService.getCommentsByBookId(
            bookId, PageRequest.of(page, size)
        );
        assertThat(actualComments).isEqualTo(expectedComments);
    }

    @Test
    @DisplayName("should return empty comments if book id not exist")
    void shouldReturnEmptyCommentsIfBookIdNotExist() {
        int page = 1;
        int size = 2;
        long bookId = 1L;
        when(bookDao.findById(bookId)).thenReturn(Optional.empty());
        Page<BookComment> expectedComments = Page.empty();
        Page<BookComment> actualComments = commentService.getCommentsByBookId(
            bookId, PageRequest.of(page, size)
        );
        assertThat(actualComments).isEqualTo(expectedComments);
    }

    @Test
    @DisplayName("should update comment's content")
    void shouldUpdateComment() {
        comment.setMessage("new content");
        when(bookCommentDao.save(comment)).thenReturn(comment);
        BookComment updatedComment = commentService.update(comment);
        assertThat(updatedComment).isEqualTo(comment);
        verify(bookCommentDao, times(1)).save(comment);
    }

    @Test
    @DisplayName("should delete comment")
    void shouldDeleteComment() {
        long id = 1;
        commentService.deleteById(id);
        verify(bookCommentDao, times(1)).deleteById(id);
    }
}