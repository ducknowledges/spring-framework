package com.github.ducknowledges.bookstore.printformatter;

import com.github.ducknowledges.bookstore.domain.BookComment;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("bookFormatter")
public class BookCommentPrintFormatter implements PrintFormatter<BookComment> {

    @Override
    public String format(BookComment comment) {
        return String.format(
            "id: %d content: %s book_id: %d",
            comment.getId(),
            comment.getContent(),
            comment.getBook().getId());
    }

    @Override
    public String format(List<BookComment> comments) {
        StringBuilder stringBuilder = new StringBuilder("Comments:" + System.lineSeparator());
        for (BookComment comment : comments) {
            stringBuilder
                .append(format(comment))
                .append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }
}
