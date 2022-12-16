package online.javaclass.bookstore.controller.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import online.javaclass.bookstore.controller.command.Command;

public class RegisterFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/register.jsp";
    }
}
