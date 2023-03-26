package online.javaclass.bookstore.controller.command.impl;

import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

@Controller("error")
public class ErrorCommand implements Command {

    @LogInvocation
    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/error.jsp";
    }
}
