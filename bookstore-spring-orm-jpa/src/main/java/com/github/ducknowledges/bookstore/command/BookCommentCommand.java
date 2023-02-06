package com.github.ducknowledges.bookstore.command;

import com.github.ducknowledges.bookstore.domain.BookComment;
import com.github.ducknowledges.bookstore.printformatter.PrintFormatter;
import com.github.ducknowledges.bookstore.service.BookCommentService;
import com.github.ducknowledges.bookstore.service.BookService;
import java.util.List;
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
        return bookService.getBook(bookId)
            .map(b -> {
                BookComment comment = new BookComment(content, b);
                BookComment createdComment = commentService.createComment(comment);
                return "Comment was created: " + printFormatter.format(createdComment);
            }).orElse("Book does not exist");
    }

    @ShellMethod(value = "Read comments command", key = "read-comment")
    public String getComment(@ShellOption long commentId) {
        return commentService.getComment(commentId)
            .map(printFormatter::format)
            .orElse("Comment doesn't exist");
    }

    @ShellMethod(value = "Read all comments command", key = "read-comments")
    public String getComments(@ShellOption long fromId,
                              @ShellOption long toId) {
        List<BookComment> comments = commentService.getComments(fromId, toId);
        return printFormatter.format(comments);
    }

    @ShellMethod(value = "Read all comments by book id command", key = "read-comments")
    public String getCommentsByBookId(@ShellOption long bookId) {
        List<BookComment> comments = commentService.getCommentsByBookId(bookId);
        return printFormatter.format(comments);
    }

    @ShellMethod(value = "Update comment content by id command", key = "update-comment")
    public String update(@ShellOption long commentId,
                         @ShellOption String content) {
        return commentService.getComment(commentId)
            .map(comment -> {
                comment.setContent(content);
                return commentService.update(comment);
            })
            .map(comment -> "Comment was updated: " + printFormatter.format(comment))
            .orElse("Comment does not exist");
    }

    @ShellMethod(value = "Delete book command", key = "delete-comment")
    public String delete(@ShellOption int bookId) {
        bookService.delete(bookId);
        return "Book was deleted";
    }
}
