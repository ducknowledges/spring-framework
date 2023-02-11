package com.github.ducknowledges.bookstore.printformatter;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.domain.Genre;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@SpringBootTest
@DisplayName("Class GenrePrintFormatter")
class GenrePrintFormatterTest {

    @Autowired
    GenrePrintFormatter genreFormatter;

    @Test
    @DisplayName("should format genres to text")
    void shouldFormatGenresToText() {
        Genre genre1 = new Genre(1L, "genre1");
        Genre genre2 = new Genre(2L, "genre2");

        String expected = "Genres:" + System.lineSeparator()
            + "id: " + genre1.getId() + " name: " + genre1.getName()
            + System.lineSeparator()
            + "id: " + genre2.getId() + " name: " + genre2.getName()
            + System.lineSeparator()
            + "Total pages: 1"
            + System.lineSeparator();;
        Page<Genre> genrePage = new PageImpl<>(List.of(genre1, genre2));
        String actual = genreFormatter.format(genrePage);
        assertThat(actual).isEqualTo(expected);
    }
}