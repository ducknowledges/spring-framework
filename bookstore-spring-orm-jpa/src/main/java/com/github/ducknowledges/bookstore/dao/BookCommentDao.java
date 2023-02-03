package com.github.ducknowledges.bookstore.dao;

import com.github.ducknowledges.bookstore.dao.crud.DeleteDao;
import com.github.ducknowledges.bookstore.dao.crud.ReadDao;
import com.github.ducknowledges.bookstore.dao.crud.SaveDao;
import com.github.ducknowledges.bookstore.domain.BookComment;
import java.util.List;

public interface BookCommentDao extends SaveDao<BookComment>, ReadDao<BookComment>, DeleteDao {

    List<BookComment> readAllByBookId(long bookId);

}
