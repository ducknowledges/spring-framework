package com.github.ducknowledges.bookstore.dao.mapper;

import com.github.ducknowledges.bookstore.domain.Author;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorRowMapper implements RowMapper<Author> {
    @Override
    public Author mapRow(ResultSet rs, int rowNumber) throws SQLException {
        return new Author(rs.getInt("id"), rs.getString("name"));
    }
}
