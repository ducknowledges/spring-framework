package com.github.ducknowledges.bookstore.command;

import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.domain.BookComment;
import com.github.ducknowledges.bookstore.domain.Genre;
import com.github.ducknowledges.bookstore.printformatter.PrintFormatter;
import com.github.ducknowledges.bookstore.service.BookCommentService;
import com.github.ducknowledges.bookstore.service.BookService;
import java.util.List;
import java.util.Optional;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class BookCommentCommand {

    private final BookCommentService commentService;
    private final BookService bookService;
    private final PrintFormatter<BookComment> printFormatter;

    public BookCommentCommand(BookCommentService commentService,
                              BookService bookService,
                              PrintFormatter<BookComment> printFormatter) {
        this.commentService = commentService;
        this.bookService = bookService;
        this.printFormatter = printFormatter;
    }

    @ShellMethod(value = "Create comment command", key = "create-comment")
    public String createComment(@ShellOption String content,
                                @ShellOption long bookId) {
        Optional<Book> book = bookService.getBook(bookId);
        if (book.isEmpty()) {
            return  "Book does not exist";
        }
        BookComment createdComment = commentService.createComment(
            new BookComment(content, book.get())
        );
        return "Comment was created: " + printFormatter.format(createdComment);
    }

    @ShellMethod(value = "Read comments command", key = "read-comment")
    public String getComment(@ShellOption long commentId) {
        Optional<BookComment> comment = commentService.getComment(commentId);
        if (comment.isPresent()) {
            return printFormatter.format(comment.get());
        }
        return "Comment doesn't exist";
    }

    @ShellMethod(value = "Read all comments command", key = "read-comments")
    public String getComments(@ShellOption int from,
                           @ShellOption int size) {
        List<BookComment> comments = commentService.getComments(from, size);
        return printFormatter.format(comments);
    }

    @ShellMethod(value = "Read all comments by book id command", key = "read-comments")
    public String getComments(@ShellOption long bookId) {
        List<BookComment> comments = commentService.getCommentsByBookId(bookId);
        return printFormatter.format(comments);
    }

    @ShellMethod(value = "Update comment content by id command", key = "update-comment")
    public String update(@ShellOption long commentId,
                         @ShellOption String content) {
        Optional<BookComment> comment = commentService.getComment(commentId);
        if (comment.isEmpty()) {
            return  "Comment does not exist";
        }
        BookComment updatedComment = commentService.update(
            new BookComment(commentId, content, comment.get().getBook())
        );
        return "Comment was updated: " + printFormatter.format(updatedComment);
    }

    @ShellMethod(value = "Delete book command", key = "delete-comment")
    public String delete(@ShellOption int bookId) {
        bookService.delete(bookId);
        return "Book was deleted";
    }
}
