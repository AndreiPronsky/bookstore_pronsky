package online.javaclass.bookstore.controller.command.impl.bookCommands;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.controller.command.Command;
import org.springframework.stereotype.Controller;

@Log4j2
@Controller("add_book_form")
public class AddBookFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/add_book.jsp";
    }
}
