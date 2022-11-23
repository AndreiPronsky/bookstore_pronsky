package online.javaclass.bookstore.controller.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;

@Log4j2
public class BookCommand implements Command {
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
