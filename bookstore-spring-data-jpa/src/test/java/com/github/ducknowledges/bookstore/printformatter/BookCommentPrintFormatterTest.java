package com.github.ducknowledges.bookstore.printformatter;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.domain.BookComment;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@SpringBootTest
@DisplayName("Class BookCommentPrintFormatter")
class BookCommentPrintFormatterTest {

    @Autowired
    BookCommentPrintFormatter commentFormatter;

    @Test
    @DisplayName("should format book comments to text")
    void shouldFormatBookCommentToText() {
        Book book = new Book();
        book.setName("book name");
        BookComment comment1 = new BookComment(1L, "book comment 1", book);
        BookComment comment2 = new BookComment(2L, "book comment 2", book);

        String expected = "Comments:" + System.lineSeparator()
            + "id: " + comment1.getId()
            + " content: " + comment1.getContent()
            + " book: " + comment1.getBook().getName()
            + System.lineSeparator()
            + "id: " + comment2.getId()
            + " content: " + comment2.getContent()
            + " book: " + comment2.getBook().getName()
            + System.lineSeparator()
            + "Total pages: 1"
            + System.lineSeparator();
        Page<BookComment> bookCommentPage = new PageImpl<>(List.of(comment1, comment2));
        String actual = commentFormatter.format(bookCommentPage);
        assertThat(actual).isEqualTo(expected);
    }
}