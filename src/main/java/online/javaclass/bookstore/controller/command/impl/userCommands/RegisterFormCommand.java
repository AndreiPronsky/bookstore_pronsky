package online.javaclass.bookstore.controller.command.impl.userCommands;

import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

@Controller("register_form")
public class RegisterFormCommand implements Command {

    @LogInvocation
    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/register.jsp";
    }
}
