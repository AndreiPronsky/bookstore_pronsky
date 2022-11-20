package online.javaclass.bookstore.controller;

import jakarta.servlet.http.HttpServletRequest;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BookCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private final BookService bookService;

    public BookCommand(BookService bookService) {
        this.bookService = bookService;
    }
    @Override
    public String execute(HttpServletRequest req) {
            Long id = processId(req);
            BookDto book = bookService.getById(id);
            req.setAttribute("book", book);
            return "jsp/book.jsp";
    }

    private Long processId(HttpServletRequest req) {
        try {
            String rawId = req.getParameter("id");
            return Long.parseLong(rawId);
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }
}
