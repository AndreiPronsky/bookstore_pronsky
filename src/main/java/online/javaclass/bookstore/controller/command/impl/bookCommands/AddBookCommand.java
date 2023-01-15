package online.javaclass.bookstore.controller.command.impl.bookCommands;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.controller.FrontController;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.exceptions.ValidationException;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;

/**
 * The command is used to create a new book
 *
 * @author Andrei Pronsky
 * {@link Command}  invokes method execute() with the request , response  and returns jsp
 */
@Log4j2
@RequiredArgsConstructor
public class AddBookCommand implements Command {
    private final BookService bookService;
    private final BookCommandUtils commandUtils = new BookCommandUtils();

    /**
     * Takes input parameters from the HttpServletRequest, and sends it to service layer if validation is passed.
     *
     * @param req - request from client to get parameters to work with
     * @return If input parameters match the requirements sends user to a created book page.
     * If input parameters don't match requirements, redirects user to add_book.jsp with description of what exactly
     *went wrong.
     */
    @Override
    public String execute(HttpServletRequest req) {
        String page;
        HttpSession session = req.getSession();
        session.removeAttribute("validationMessages");
        try {
            BookDto book = commandUtils.setBookParameters(req);
            BookDto newBook = bookService.create(book);
            req.setAttribute("book", newBook);
            page = "controller?command=book&id=" + newBook.getId();
        } catch (ValidationException e) {
            session.setAttribute("validationMessages", e.getMessages());
            page = "controller?command=add_book_form";
        }
        return FrontController.REDIRECT + page;
    }
}
