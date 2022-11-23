package online.javaclass.bookstore.controller.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BooksCommand implements Command {

    private static final Logger log = LogManager.getLogger();
    private final BookService bookService;

    public BooksCommand(BookService bookService) {
        this.bookService = bookService;
    }
    @Override
    public String execute(HttpServletRequest req) {
        List<BookDto> books;
        String rawAuthor = req.getParameter("author");
        if (rawAuthor == null) {
            books = bookService.getAll();
        } else {
            String author = reformatAuthor(rawAuthor);
            books = bookService.getByAuthor(author);
        }
        req.setAttribute("books", books);
        return "jsp/books.jsp";
    }

    private String reformatAuthor(String rawAuthor) {
        return rawAuthor.replace("%20", " ");
    }
}
