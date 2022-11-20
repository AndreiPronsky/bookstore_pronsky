package online.javaclass.bookstore.controller.command;

import jakarta.servlet.http.HttpServletRequest;

public interface Command {
    String execute(HttpServletRequest req);
}
