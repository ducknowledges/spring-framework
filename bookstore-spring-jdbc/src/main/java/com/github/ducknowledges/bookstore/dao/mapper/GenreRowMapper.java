package com.github.ducknowledges.bookstore.dao.mapper;

import com.github.ducknowledges.bookstore.domain.Genre;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class GenreRowMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet rs, int rowNumber) throws SQLException {
        return new Genre(rs.getLong("id"), rs.getString("name"));
    }
}