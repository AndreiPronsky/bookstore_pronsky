package online.javaclass.bookstore.controller.command.impl.orderCommands;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.controller.command.Command;
import org.springframework.stereotype.Controller;

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
