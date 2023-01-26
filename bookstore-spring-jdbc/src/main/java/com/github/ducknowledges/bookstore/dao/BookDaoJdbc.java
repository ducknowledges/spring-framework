package com.github.ducknowledges.bookstore.dao;

import static java.util.Objects.isNull;

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
    public int count() {
        Integer count = jdbc.getJdbcOperations()
            .queryForObject("select count(*) from BOOK", Integer.class);
        return isNull(count) ? 0 : count;
    }

    @Override
    public void create(Book book) {
        String name = book.getName();
        int authorId = book.getAuthor().getId();
        int genreId = book.getGenre().getId();
        jdbc.update(
            "insert into BOOK(NAME, AUTHOR_ID, GENRE_ID)\n"
                + "values (:name, :authorId, :genreId)",
            Map.of("name", name, "authorId", authorId, "genreId", genreId));
    }

    @Override
    public Optional<Book> readById(int id) {
        Book book;
        try {
            book = jdbc.queryForObject(
                "select\n"
                    + "    BOOK.ID as id,\n"
                    + "    BOOK.NAME as name,\n"
                    + "    BOOK.AUTHOR_ID as author_id,\n"
                    + "    A.NAME as author_name,\n"
                    + "    BOOK.GENRE_ID as genre_id,\n"
                    + "    G.NAME as genre_name\n"
                    + "from BOOK\n"
                    + "         join AUTHOR A on A.ID = BOOK.AUTHOR_ID\n"
                    + "         join GENRE G on G.ID = BOOK.GENRE_ID\n"
                    + "where BOOK.ID = :id",
                Map.of("id", id),
                bookMapper);
        } catch (EmptyResultDataAccessException e) {
            book = null;
        }
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> readAll() {
        return jdbc.query(
            "select\n"
                + "    BOOK.ID as id,\n"
                + "    BOOK.NAME as name,\n"
                + "    BOOK.AUTHOR_ID as author_id,\n"
                + "    A.NAME as author_name,\n"
                + "    BOOK.GENRE_ID as genre_id,\n"
                + "    G.NAME as genre_name\n"
                + "from BOOK\n"
                + "         join AUTHOR A on A.ID = BOOK.AUTHOR_ID\n"
                + "         join GENRE G on G.ID = BOOK.GENRE_ID",
            bookMapper);
    }

    @Override
    public void update(Book book) {
        int id = book.getId();
        String name = book.getName();
        int authorId = book.getAuthor().getId();
        int genreId = book.getGenre().getId();
        jdbc.update(
            "update BOOK\n"
                + "SET NAME      = :name,\n"
                + "    AUTHOR_ID = :authorId,\n"
                + "    GENRE_ID  = :genreId\n"
                + "where ID = :id",
            Map.of("id", id, "name", name, "authorId", authorId, "genreId", genreId));
    }

    @Override
    public void delete(int id) {
        jdbc.update("delete from BOOK where ID = :id", Map.of("id", id));
    }


}
