package online.javaclass.bookstore.controller.command.impl.userCommands;

import jakarta.servlet.http.HttpServletRequest;
import online.javaclass.bookstore.controller.command.Command;

public class LoginFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/login.jsp";
    }
}
