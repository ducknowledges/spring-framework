package com.github.ducknowledges.bookstore.printformatter;

import com.github.ducknowledges.bookstore.domain.Author;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("authorFormatter")
public class AuthorPrintFormatter implements PrintFormatter<Author> {
    @Override
    public String format(Author author) {
        return String.format("id: %d name: %s", author.getId(), author.getName());
    }

    @Override
    public String format(List<Author> authors) {
        StringBuilder stringBuilder = new StringBuilder("Authors:" + System.lineSeparator());
        for (Author author : authors) {
            stringBuilder
                .append(format(author))
                .append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }
}
