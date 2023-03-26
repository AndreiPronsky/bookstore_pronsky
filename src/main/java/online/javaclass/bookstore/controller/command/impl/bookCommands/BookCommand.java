package online.javaclass.bookstore.controller.command.impl.bookCommands;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;

import javax.servlet.http.HttpServletRequest;

/**
 * The command is used to set book parameters to HttpServletRequest for further creation of book page on clients side
 *
 * @author Andrei Pronsky
 */

@RequiredArgsConstructor
//@Controller("book")
public class BookCommand implements Command {
    private final BookService bookService;

    /**
     * Takes input parameters from the HttpServletRequest, and sends it to service layer to get existing book.
     *
     * @param req - request from client to get book id to work with
     * @return jsp page with pre-set request attribute of book id
     */
    @LogInvocation
    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        BookDto book = bookService.getById(id);
        req.setAttribute("book", book);
        return "jsp/book.jsp";
    }
}
