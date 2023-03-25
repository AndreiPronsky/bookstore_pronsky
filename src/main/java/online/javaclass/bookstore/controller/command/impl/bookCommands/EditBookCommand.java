package online.javaclass.bookstore.controller.command.impl.bookCommands;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.FrontController;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.exceptions.ValidationException;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;
import org.springframework.stereotype.Controller;

/**
 * The command is used to edit existing book
 *
 * @author Andrei Pronsky
 */
@RequiredArgsConstructor
@Controller("edit_book")
public class EditBookCommand implements Command {
    private final BookService bookService;
    private final BookCommandUtils commandUtils;

    /**
     * Takes input parameters from the HttpServletRequest, and sends it to service layer if validation is passed.
     * If input parameters don't match requirements, redirects user to edit_book.jsp with description of what exactly
     * went wrong.
     *
     * @param req - request from client to get parameters to work with
     * @return If book parameters meet the requirements redirects user to a page with pre-set
     * of edited book to a request.
     * If input parameters don't match requirements, redirects user to edit_book.jsp with description of what exactly
     * went wrong
     */
    @LogInvocation
    @Override
    public String execute(HttpServletRequest req) {
        String page;
        HttpSession session = req.getSession();
        session.removeAttribute("validationMessages");
        long id = Long.parseLong(req.getParameter("id"));
        try {
            BookDto book = commandUtils.setBookParameters(req);
            book.setId(id);
            BookDto updatedBook = bookService.update(book);
            req.setAttribute("book", updatedBook);
            page = "controller?command=book&id=" + updatedBook.getId();
        } catch (ValidationException e) {
            session.setAttribute("validationMessages", e.getMessages());
            page = "controller?command=edit_book_form&id=" + id;
        }
        return FrontController.REDIRECT + page;
    }
}
