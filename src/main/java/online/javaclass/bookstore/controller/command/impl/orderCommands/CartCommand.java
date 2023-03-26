package online.javaclass.bookstore.controller.command.impl.orderCommands;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller("cart")
public class CartCommand implements Command {

    @LogInvocation
    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        if (session.getAttribute("user") == null) {
            return "jsp/login.jsp";
        }
        if (session.getAttribute("cart") == null) {
            return "jsp/confirm_order.jsp";
        }
        return "jsp/confirm_order.jsp";
    }
}
