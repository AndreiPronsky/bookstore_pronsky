package online.javaclass.bookstore.controller.command.impl.userCommands;

import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller("logout")
public class LogoutCommand implements Command {

    @LogInvocation
    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        session.invalidate();
        return "index.jsp";
    }
}
