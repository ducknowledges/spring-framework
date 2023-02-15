package com.github.ducknowledges.bookstore.controller;

import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.dto.BookDto;
import com.github.ducknowledges.bookstore.service.AuthorService;
import com.github.ducknowledges.bookstore.service.BookService;
import com.github.ducknowledges.bookstore.service.GenreService;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;


    public BookController(BookService bookService,
                          AuthorService authorService,
                          GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @GetMapping("/books")
    public String booksPage(@RequestParam("page") Optional<Integer> optionalPage,
                            @RequestParam("size") Optional<Integer> optionalSize,
                            Model model) {
        int page = optionalPage.filter(p -> p > 0).orElse(1);
        int size = optionalSize.filter(s -> s == 5).orElse(5);
        Page<Book> books = bookService.getBooks(page - 1, size);
        model.addAttribute("books", books.getContent())
            .addAttribute("totalPages", books.getTotalPages())
            .addAttribute("currentPage", page);
        return "pages/books";
    }

    @GetMapping("/book/create")
    public String createPage(Model model) {
        prepareBookControllerModel(new BookDto(), model);
        return "pages/book/create";
    }

    @PostMapping("/book/create")
    public String createBook(@Valid @ModelAttribute("book") BookDto bookDto,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            prepareBookControllerModel(bookDto, model);
            return "pages/book/create";
        }
        bookService.create(bookDto.toDomainObject());
        return "redirect:/books";
    }

    @GetMapping("/book/edit/{id}")
    public String editPage(@PathVariable("id") long id,
                           Model model) {
        BookDto bookDto = bookService.getBook(id).map(BookDto::toDto).orElseThrow();
        prepareBookControllerModel(bookDto, model);
        return "pages/book/edit";
    }

    @PostMapping("/book/edit")
    public String saveBook(@Valid @ModelAttribute("book") BookDto bookDto,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            prepareBookControllerModel(bookDto, model);
            return "pages/book/edit";
        }
        bookService.save(bookDto.toDomainObject());
        return "redirect:/books";
    }

    @GetMapping("/book/delete/{id}")
    public String deleteBook(@PathVariable("id") long id) {
        bookService.deleteById(id);
        return "redirect:/books";
    }

    private void prepareBookControllerModel(BookDto bookDto, Model model) {
        model.addAttribute("book", bookDto)
            .addAttribute("authors", authorService.getAllAuthors())
            .addAttribute("genres", genreService.getAllGenres());
    }
}
