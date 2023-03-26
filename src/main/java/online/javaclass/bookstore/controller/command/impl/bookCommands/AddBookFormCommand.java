package online.javaclass.bookstore.controller.command.impl.bookCommands;

import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.platform.logging.LogInvocation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The command is used just to redirect user to form for adding new book
 *
 * @author Andrei Pronsky
 */

//@Controller("add_book_form")
public class AddBookFormCommand implements Command {
    @LogInvocation
    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.removeAttribute("validationMessages");
        return "jsp/add_book.jsp";
    }
}
