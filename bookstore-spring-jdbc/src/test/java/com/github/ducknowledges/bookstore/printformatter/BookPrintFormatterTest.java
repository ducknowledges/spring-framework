package com.github.ducknowledges.bookstore.printformatter;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.domain.Genre;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("Class BookPrintFormatter")
class BookPrintFormatterTest {

    @Autowired
    BookPrintFormatter bookFormatter;

    @Test
    @DisplayName("should format books to text")
    void testFormat() {
        Author author1 = new Author(1L, "author");
        Genre genre1 = new Genre(1L, "genre");
        Book book1 = new Book(1L, "book", author1, genre1);

        Author author2 = new Author(2L, "author");
        Genre genre2 = new Genre(2L, "genre");
        Book book2 = new Book(2L, "book", author2, genre2);

        StringBuilder stringBuilder = new StringBuilder("Books:" + System.lineSeparator());
        stringBuilder
            .append("id: " + book1.getId()
                + " name: " + book1.getName()
                + " author: " + book1.getAuthor().getName()
                + " genre: " + book1.getGenre().getName())
            .append(System.lineSeparator())
            .append("id: " + book2.getId()
                + " name: " + book2.getName()
                + " author: " + book2.getAuthor().getName()
                + " genre: " + book2.getGenre().getName())
            .append(System.lineSeparator());

        String expected = stringBuilder.toString();
        String actual = bookFormatter.format(List.of(book1, book2));
        assertThat(actual).isEqualTo(expected);
    }
}