package online.javaclass.bookstore.controller.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.controller.command.Command;
import org.springframework.stereotype.Controller;

@Log4j2
@Controller("error")
public class ErrorCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/error.jsp";
    }
}
