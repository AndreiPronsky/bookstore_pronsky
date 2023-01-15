package online.javaclass.bookstore.controller.command.impl.bookCommands;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.controller.command.Command;
/**
 * The command is used just to redirect user to form for adding new book
 * @author Andrei Pronsky
 */
@Log4j2
public class AddBookFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/add_book.jsp";
    }
}
