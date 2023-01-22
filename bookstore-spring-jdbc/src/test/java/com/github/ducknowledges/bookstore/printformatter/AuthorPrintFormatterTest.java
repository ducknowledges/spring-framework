package com.github.ducknowledges.bookstore.printformatter;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ducknowledges.bookstore.domain.Author;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("Class AuthorPrintFormatter")
class AuthorPrintFormatterTest {

    @Autowired
    AuthorPrintFormatter authorFormatter;

    @Test
    @DisplayName("should format authors to text")
    void testFormat() {
        Author author1 = new Author(1, "author1");
        Author author2 = new Author(2, "author2");

        StringBuilder stringBuilder = new StringBuilder("Authors:" + System.lineSeparator());
        stringBuilder
            .append("id: " + author1.getId() + " name: " + author1.getName())
            .append(System.lineSeparator())
            .append("id: " + author2.getId() + " name: " + author2.getName())
            .append(System.lineSeparator());

        String expected = stringBuilder.toString();
        String actual = authorFormatter.format(List.of(author1, author2));
        assertThat(actual).isEqualTo(expected);
    }
}