package online.javaclass.bookstore.controller.command.impl.orderCommands;

import jakarta.servlet.http.HttpServletRequest;
import online.javaclass.bookstore.controller.command.Command;

public class CorrectOrderCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        String attributeName = "items";
        OrderCommandUtils.correctItemQuantity(req, attributeName);
        return "jsp/edit_order.jsp";
    }
}
