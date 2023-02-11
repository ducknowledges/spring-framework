package com.github.ducknowledges.bookstore.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.bookstore.dao.AuthorDao;
import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.printformatter.PrintFormatter;
import com.github.ducknowledges.bookstore.service.AuthorService;
import com.github.ducknowledges.bookstore.service.AuthorServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@SpringBootTest
@DisplayName("Class BookAuthorCommandTest")
class BookAuthorCommandTest {

    @MockBean
    private AuthorServiceImpl authorService;

    @MockBean
    private PrintFormatter<Author> printformatter;

    @Autowired
    private BookAuthorCommand bookAuthorCommand;

    @Test
    @DisplayName("should return authors formatted to string")
    void shouldFormatAuthorsToString() {
        int page = 0;
        int size = 1;
        Page<Author> authorPage = new PageImpl<>(List.of());
        when(authorService.getAuthors(page, size)).thenReturn(authorPage);
        when(printformatter.format(authorPage)).thenReturn("author");
        String expected = "author";
        String actual = bookAuthorCommand.getAuthors(page, size);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("should return authors formatted to string")
    void shouldFormatAuthorToString() {
        long authorId = 1L;
        Author author = new Author(1L, "name");
        when(authorService.getAuthor(authorId)).thenReturn(Optional.of(author));
        when(printformatter.format(author)).thenReturn("author");
        String expected = "author";
        String actual = bookAuthorCommand.getAuthor(authorId);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("should return authors does not exist")
    void shouldReturnAuthorDoesNotExist() {
        long authorId = 1L;
        when(authorService.getAuthor(authorId)).thenReturn(Optional.empty());
        String expected = "Author doesn't exist";
        String actual = bookAuthorCommand.getAuthor(authorId);
        assertThat(actual).isEqualTo(expected);
    }
}