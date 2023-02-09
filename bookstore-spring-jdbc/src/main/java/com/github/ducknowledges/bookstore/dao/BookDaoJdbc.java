package com.github.ducknowledges.bookstore.dao;

import com.github.ducknowledges.bookstore.domain.Book;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcTemplate jdbc;
    private final RowMapper<Book> bookMapper;

    public BookDaoJdbc(NamedParameterJdbcTemplate jdbc, RowMapper<Book> bookMapper) {
        this.jdbc = jdbc;
        this.bookMapper = bookMapper;
    }

    @Override
    public void create(Book book) {
        String name = book.getName();
        long authorId = book.getAuthor().getId();
        long genreId = book.getGenre().getId();
        jdbc.update(
            "insert into BOOK(NAME, AUTHOR_ID, GENRE_ID)\n"
                + "values (:name, :authorId, :genreId)",
            Map.of("name", name,
                "authorId", authorId,
                "genreId", genreId));
    }

    @Override
    public Boolean existsById(long id) {
        return jdbc.queryForObject("select exists(select id from book where id = :id)",
            Map.of("id", id), Boolean.class);
    }

    @Override
    public Optional<Book> findById(long id) {
        Book book;
        try {
            book = jdbc.queryForObject(
                "select\n"
                    + "    book.id as id,\n"
                    + "    book.name as name,\n"
                    + "    book.author_id as author_id,\n"
                    + "    a.name as author_name,\n"
                    + "    book.genre_id as genre_id,\n"
                    + "    g.name as genre_name\n"
                    + "from book\n"
                    + "         join author a on a.id = book.author_id\n"
                    + "         join genre g on g.id = book.genre_id\n"
                    + "where book.id = :id",
                Map.of("id", id),
                bookMapper);
        } catch (EmptyResultDataAccessException e) {
            book = null;
        }
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll(long fromId, long toId) {
        return jdbc.query(
            "select\n"
                + "    book.id as id,\n"
                + "    book.name as name,\n"
                + "    book.author_id as author_id,\n"
                + "    a.name as author_name,\n"
                + "    book.genre_id as genre_id,\n"
                + "    g.name as genre_name\n"
                + "from book\n"
                + "         join author a on a.id = book.author_id\n"
                + "         join genre g on g.id = book.genre_id\n"
                + "where book.id >= :fromId and book.id <= :toId",
            Map.of("fromId", fromId, "toId", toId),
            bookMapper);
    }

    @Override
    public void update(Book book) {
        long id = book.getId();
        String name = book.getName();
        long authorId = book.getAuthor().getId();
        long genreId = book.getGenre().getId();
        jdbc.update(
            "update book\n"
                + "SET name      = :name,\n"
                + "    author_id = :authorId,\n"
                + "    genre_id  = :genreId\n"
                + "where id = :id",
            Map.of("id", id,
                "name", name,
                "authorId", authorId,
                "genreId", genreId)
        );
    }

    @Override
    public void deleteById(long id) {
        jdbc.update(
            "delete from book where id = :id",
            Map.of("id", id)
        );
    }
}
