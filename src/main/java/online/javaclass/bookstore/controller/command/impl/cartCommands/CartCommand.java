package online.javaclass.bookstore.controller.command.impl.cartCommands;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.command.Command;

@RequiredArgsConstructor
public class CartCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        if (session.getAttribute("cart") == null) {
            return "jsp/cart.jsp";
        }
        if (session.getAttribute("user") == null) {
            return "jsp/login.jsp";
        }
        return "jsp/confirm_order.jsp";
    }
}
