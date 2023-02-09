package com.github.ducknowledges.bookstore.printformatter;

import com.github.ducknowledges.bookstore.domain.BookComment;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@Qualifier("bookFormatter")
public class BookCommentPrintFormatter implements PrintFormatter<BookComment> {

    @Override
    public String format(BookComment comment) {
        return String.format(
            "id: %d content: %s book: %s",
            comment.getId(),
            comment.getContent(),
            comment.getBook().getName());
    }

    @Override
    public String format(Page<BookComment> comments) {
        StringBuilder stringBuilder = new StringBuilder("Comments:" + System.lineSeparator());
        for (BookComment comment : comments.getContent()) {
            stringBuilder
                .append(format(comment))
                .append(System.lineSeparator());
        }
        stringBuilder
            .append("Total pages: ")
            .append(comments.getTotalPages())
            .append(System.lineSeparator());
        return stringBuilder.toString();
    }
}
