package com.github.ducknowledges.bookstore.printformatter;

import com.github.ducknowledges.bookstore.domain.Genre;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@Qualifier("genreFormatter")
public class GenrePrintFormatter implements PrintFormatter<Genre> {
    @Override
    public String format(Genre genre) {
        return String.format("id: %d name: %s", genre.getId(), genre.getName());
    }

    @Override
    public String format(Page<Genre> genres) {
        StringBuilder stringBuilder = new StringBuilder("Genres:" + System.lineSeparator());
        for (Genre genre : genres.getContent()) {
            stringBuilder
                .append(format(genre))
                .append(System.lineSeparator());
        }
        stringBuilder
            .append("Total pages: ")
            .append(genres.getTotalPages())
            .append(System.lineSeparator());
        return stringBuilder.toString();
    }
}
