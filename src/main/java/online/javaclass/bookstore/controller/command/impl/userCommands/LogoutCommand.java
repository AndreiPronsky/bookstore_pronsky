package online.javaclass.bookstore.controller.command.impl.userCommands;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import online.javaclass.bookstore.controller.command.Command;
import org.springframework.stereotype.Controller;

@Controller("logout")
public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        session.invalidate();
        return "index.jsp";
    }
}
