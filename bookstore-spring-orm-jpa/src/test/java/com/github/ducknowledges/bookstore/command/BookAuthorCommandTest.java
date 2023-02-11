package com.github.ducknowledges.bookstore.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.printformatter.PrintFormatter;
import com.github.ducknowledges.bookstore.service.AuthorServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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
        long fromId = 0;
        long toId = 1;
        List<Author> authors = List.of();
        when(authorService.getAuthors(fromId, toId)).thenReturn(authors);
        when(printformatter.format(authors)).thenReturn("author");
        String expected = "author";
        String actual = bookAuthorCommand.getAuthors(fromId, toId);
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