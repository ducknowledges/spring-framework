package com.github.ducknowledges.bookstore.controller;

import com.github.ducknowledges.bookstore.domain.BookComment;
import com.github.ducknowledges.bookstore.service.BookCommentService;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookCommentController {

    private final BookCommentService commentService;

    public BookCommentController(BookCommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments")
    public String commentsPage(@RequestParam("book_id") Optional<Long> bookId,
                               @RequestParam("page") Optional<Integer> optionalPage,
                               @RequestParam("size") Optional<Integer> optionalSize,
                               Model model) {
        int page = optionalPage.filter(p -> p > 0).orElse(1);
        int size = optionalSize.filter(s -> s == 5).orElse(5);

        Page<BookComment> comments = bookId
            .map(id -> commentService.getCommentsByBookId(id, page - 1, size))
            .orElse(commentService.getComments(page - 1, size));

        model.addAttribute("comments", comments.getContent())
            .addAttribute("totalPages", comments.getTotalPages())
            .addAttribute("currentPage", page)
            .addAttribute("book_id", bookId.map(Object::toString).orElse(""));
        return "pages/comments";
    }
}
