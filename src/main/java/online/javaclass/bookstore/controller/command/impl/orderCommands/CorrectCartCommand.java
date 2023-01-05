package online.javaclass.bookstore.controller.command.impl.orderCommands;

import jakarta.servlet.http.HttpServletRequest;
import online.javaclass.bookstore.controller.command.Command;

public class CorrectCartCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        String attributeName = "cart";
        OrderCommandUtils.correctItemQuantity(req, attributeName);
        return "jsp/confirm_order.jsp";
    }
}