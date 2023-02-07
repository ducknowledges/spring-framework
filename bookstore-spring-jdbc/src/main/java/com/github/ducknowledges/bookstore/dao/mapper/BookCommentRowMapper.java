package com.github.ducknowledges.bookstore.dao.mapper;

import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.domain.BookComment;
import com.github.ducknowledges.bookstore.domain.Genre;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BookCommentRowMapper implements RowMapper<BookComment> {
    @Override
    public BookComment mapRow(ResultSet rs, int rowNumber) throws SQLException {
        long id = rs.getLong("id");
        String content = rs.getString("content");
        Author author = new Author(rs.getString("author_name"));
        Genre genre = new Genre(rs.getString("genre_name"));
        Book book = new Book(
            rs.getLong("book_id"),
            rs.getString("book_name"),
            author,
            genre
        );
        return new BookComment(id, content, book);
    }
}
