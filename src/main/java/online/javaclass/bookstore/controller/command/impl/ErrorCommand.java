package online.javaclass.bookstore.controller.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import org.springframework.stereotype.Controller;

@Controller("error")
public class ErrorCommand implements Command {

    @LogInvocation
    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/error.jsp";
    }
}
