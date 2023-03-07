package online.javaclass.bookstore.controller.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import online.javaclass.bookstore.LogInvocation;
import online.javaclass.bookstore.controller.command.Command;
import org.springframework.stereotype.Controller;

@Controller("home")
public class HomeCommand implements Command {

    @LogInvocation
    @Override
    public String execute(HttpServletRequest req) {
        return "index.jsp";
    }
}
