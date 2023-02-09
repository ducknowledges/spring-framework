package com.github.ducknowledges.bookstore.printformatter;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ducknowledges.bookstore.dao.AuthorDao;
import com.github.ducknowledges.bookstore.domain.Author;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@SpringBootTest
@DisplayName("Class AuthorPrintFormatter")
class AuthorPrintFormatterTest {

    @Autowired
    AuthorPrintFormatter authorFormatter;

    @Test
    @DisplayName("should format authors to text")
    void shouldFormatAuthorsToText() {
        Author author1 = new Author(1L, "author1");
        Author author2 = new Author(2L, "author2");

        String expected = "Authors:" + System.lineSeparator()
            + "id: " + author1.getId() + " name: " + author1.getName()
            + System.lineSeparator()
            + "id: " + author2.getId() + " name: " + author2.getName()
            + System.lineSeparator()
            + "Total pages: 1"
            + System.lineSeparator();
        Page<Author> authorPage = new PageImpl<>(List.of(author1, author2));
        String actual = authorFormatter.format(authorPage);
        assertThat(actual).isEqualTo(expected);
    }
}