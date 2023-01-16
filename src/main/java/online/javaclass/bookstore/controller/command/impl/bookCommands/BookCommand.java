package online.javaclass.bookstore.controller.command.impl.bookCommands;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;
/**
 * The command is used to set book parameters to HttpServletRequest for further creation of book page on clients side
 * @author Andrei Pronsky
 */
@Log4j2
@RequiredArgsConstructor
public class BookCommand implements Command {
    private final BookService bookService;
    /**
     * Takes input parameters from the HttpServletRequest, and sends it to service layer to get existing book.
     * @param req - request from client to get book id to work with
     * @return jsp page with pre-set request attribute of book id
     */
    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        BookDto book = bookService.getById(id);
        req.setAttribute("book", book);
        return "jsp/book.jsp";
    }
}
