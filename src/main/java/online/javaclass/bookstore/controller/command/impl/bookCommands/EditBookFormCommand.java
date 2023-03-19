package online.javaclass.bookstore.controller.command.impl.bookCommands;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.LogInvocation;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;
import org.springframework.stereotype.Controller;

/**
 * The command is used just to redirect user to form for editing existing book
 *
 * @author Andrei Pronsky
 */

@RequiredArgsConstructor
@Controller("edit_book_form")
public class EditBookFormCommand implements Command {
    private final BookService bookService;

    /**
     * Takes input parameters from the HttpServletRequest, and sends it to service layer to get existing book for
     * further editing.
     *
     * @param req - request from client to get book id to work with
     * @return jsp page with pre-set book parameters for editing.
     */
    @LogInvocation
    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        BookDto book = bookService.getById(id);
        req.setAttribute("book", book);
        return "jsp/edit_book.jsp";
    }
}
