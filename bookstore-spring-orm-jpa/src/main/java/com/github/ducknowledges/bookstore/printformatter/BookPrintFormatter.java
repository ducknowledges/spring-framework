package com.github.ducknowledges.bookstore.printformatter;

import com.github.ducknowledges.bookstore.domain.Book;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("bookFormatter")
public class BookPrintFormatter implements PrintFormatter<Book> {

    @Override
    public String format(Book book) {
        long id = book.getId();
        String name = book.getName();
        String author = book.getAuthor().getName();
        String genre = book.getGenre().getName();
        return String.format(
            "id: %d name: %s author: %s genre: %s",
            id, name, author, genre);
    }

    @Override
    public String format(List<Book> books) {
        StringBuilder stringBuilder = new StringBuilder("Books:" + System.lineSeparator());
        for (Book book : books) {
            stringBuilder
                .append(format(book))
                .append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }
}
