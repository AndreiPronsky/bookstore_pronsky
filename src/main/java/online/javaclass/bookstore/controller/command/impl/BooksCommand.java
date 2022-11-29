package online.javaclass.bookstore.controller.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;

import java.util.List;

@Log4j2
public class BooksCommand implements Command {
    private final BookService bookService;

    public BooksCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        List<BookDto> books;
        try {
            String rawAuthor = req.getParameter("author");
            if (rawAuthor == null) {
                books = bookService.getAll();
            } else {
                String author = reformatAuthor(rawAuthor);
                books = bookService.getByAuthor(author);
            }
            req.setAttribute("books", books);
            return "jsp/books.jsp";
        } catch (Exception e) {
            log.error(e.getClass() + " " + e.getMessage());
            return "jsp/error.jsp";
        }
    }

    private String reformatAuthor(String rawAuthor) {
        return rawAuthor.replace("%20", " ");
    }
}
