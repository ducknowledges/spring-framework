package com.github.ducknowledges.bookstore.dao;

import com.github.ducknowledges.bookstore.domain.BookComment;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookCommentDaoJdbc implements BookCommentDao {

    private final NamedParameterJdbcTemplate jdbc;
    private final RowMapper<BookComment> commentMapper;

    public BookCommentDaoJdbc(NamedParameterJdbcTemplate jdbc,
                              RowMapper<BookComment> commentMapper) {
        this.jdbc = jdbc;
        this.commentMapper = commentMapper;
    }

    @Override
    public void create(BookComment comment) {
        jdbc.update(
            "insert into book_comment(content, book_id)\n"
                + "values (:content, :bookId)",
            Map.of("content", comment.getContent(),
                "bookId", comment.getBook().getId()));
    }

    @Override
    public Optional<BookComment> findById(long id) {
        BookComment bookcomment;
        try {
            bookcomment = jdbc.queryForObject(
               "select c.id,\n"
                   + "       c.content,\n"
                   + "       c.book_id,\n"
                   + "       b.name as book_name,\n"
                   + "       a.name as author_name,\n"
                   + "       g.name as genre_name\n"
                   + "from book_comment as c\n"
                   + "         join book b on b.id = c.book_id\n"
                   + "         join author a on a.id = b.author_id\n"
                   + "         join genre g on g.id = b.genre_id\n"
                   + "where c.id = :id",
                Map.of("id", id),
                commentMapper);
        } catch (EmptyResultDataAccessException e) {
            bookcomment = null;
        }
        return Optional.ofNullable(bookcomment);
    }

    @Override
    public List<BookComment> findAll(long fromId, long toId) {
        return jdbc.query(
            "select c.id,\n"
                + "       c.content,\n"
                + "       c.book_id,\n"
                + "       b.name as book_name,\n"
                + "       a.name as author_name,\n"
                + "       g.name as genre_name\n"
                + "from book_comment as c\n"
                + "         join book b on b.id = c.book_id\n"
                + "         join author a on a.id = b.author_id\n"
                + "         join genre g on g.id = b.genre_id\n"
                + "where c.id >= :fromId and c.id <= :toId",
            Map.of("fromId", fromId, "toId", toId),
            commentMapper);
    }

    @Override
    public List<BookComment> findAllByBookId(long bookId) {
        return jdbc.query(
            "select c.id,\n"
                + "       c.content,\n"
                + "       c.book_id,\n"
                + "       b.name as book_name,\n"
                + "       a.name as author_name,\n"
                + "       g.name as genre_name\n"
                + "from book_comment as c\n"
                + "         join book b on b.id = c.book_id\n"
                + "         join author a on a.id = b.author_id\n"
                + "         join genre g on g.id = b.genre_id\n"
                + "where c.book_id = :bookId",
            Map.of("bookId", bookId),
            commentMapper);
    }


    @Override
    public void updateContent(long id, String content) {
        jdbc.update(
            "update book_comment\n"
                + "SET content = :content\n"
                + "where id = :id",
            Map.of("id", id,
                "content", content)
        );
    }

    @Override
    public void deleteById(long id) {
        jdbc.update(
            "delete from book_comment where id = :id",
            Map.of("id", id)
        );
    }

    @Override
    public void deleteAllByBookId(long bookId) {
        jdbc.update(
            "delete from book_comment where book_id = :bookId",
            Map.of("bookId", bookId)
        );
    }
}
