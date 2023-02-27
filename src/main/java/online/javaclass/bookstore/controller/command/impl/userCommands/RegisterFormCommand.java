package online.javaclass.bookstore.controller.command.impl.userCommands;

import jakarta.servlet.http.HttpServletRequest;
import online.javaclass.bookstore.controller.command.Command;
import org.springframework.stereotype.Controller;

@Controller("register_form")
public class RegisterFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/register.jsp";
    }
}
