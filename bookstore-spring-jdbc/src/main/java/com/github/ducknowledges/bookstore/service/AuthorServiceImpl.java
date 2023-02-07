package com.github.ducknowledges.bookstore.service;

import com.github.ducknowledges.bookstore.dao.AuthorDao;
import com.github.ducknowledges.bookstore.domain.Author;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public Optional<Author> getAuthor(long id) {
        return authorDao.findById(id);
    }

    @Override
    public List<Author> getAuthors(long fromId, long toId) {
        return authorDao.findAll(fromId, toId);
    }
}
