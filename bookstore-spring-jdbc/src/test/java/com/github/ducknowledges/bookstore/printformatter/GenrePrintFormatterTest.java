package com.github.ducknowledges.bookstore.printformatter;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.domain.Genre;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("Class GenrePrintFormatter")
class GenrePrintFormatterTest {

    @Autowired
    GenrePrintFormatter genreFormatter;

    @Test
    @DisplayName("should format genres to text")
    void testFormat() {
        Genre genre1 = new Genre(1L, "genre1");
        Genre genre2 = new Genre(2L, "genre2");

        StringBuilder stringBuilder = new StringBuilder("Genres:" + System.lineSeparator());
        stringBuilder
            .append("id: " + genre1.getId() + " name: " + genre1.getName())
            .append(System.lineSeparator())
            .append("id: " + genre2.getId() + " name: " + genre2.getName())
            .append(System.lineSeparator());

        String expected = stringBuilder.toString();
        String actual = genreFormatter.format(List.of(genre1, genre2));
        assertThat(actual).isEqualTo(expected);
    }
}