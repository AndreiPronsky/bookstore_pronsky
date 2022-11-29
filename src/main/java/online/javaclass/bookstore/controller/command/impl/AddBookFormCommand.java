package online.javaclass.bookstore.controller.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.controller.command.Command;

@Log4j2
public class AddBookFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/add_book.jsp";
    }
}
