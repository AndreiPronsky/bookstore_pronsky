package online.javaclass.bookstore.controller.command.impl.userCommands;

import jakarta.servlet.http.HttpServletRequest;
import online.javaclass.bookstore.controller.command.Command;
import org.springframework.stereotype.Controller;

@Controller("login_form")
public class LoginFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/login.jsp";
    }
}
