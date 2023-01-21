package com.github.ducknowledges.bookstore.dao.mapper;

import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.domain.Genre;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BookRowMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNumber) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        Author author = new Author(rs.getInt("author_id"), rs.getString("author_name"));
        Genre genre = new Genre(rs.getInt("author_id"), rs.getString("genre_name"));
        return new Book(id, name, author, genre);
    }
}
