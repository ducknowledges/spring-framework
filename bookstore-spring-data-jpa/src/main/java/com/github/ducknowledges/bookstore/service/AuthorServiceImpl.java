package com.github.ducknowledges.bookstore.service;

import com.github.ducknowledges.bookstore.dao.AuthorDao;
import com.github.ducknowledges.bookstore.domain.Author;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Author> getAuthor(long id) {
        return authorDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Author> getAuthors(int page, int size) {
        return authorDao.findAll(PageRequest.of(page, size));
    }
}
