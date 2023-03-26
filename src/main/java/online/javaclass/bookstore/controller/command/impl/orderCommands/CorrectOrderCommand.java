package online.javaclass.bookstore.controller.command.impl.orderCommands;

import jakarta.servlet.http.HttpServletRequest;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import org.springframework.stereotype.Controller;

@Controller("corr_order")
public class CorrectOrderCommand implements Command {
    @LogInvocation
    @Override
    public String execute(HttpServletRequest req) {
        String attributeName = "items";
        OrderCommandUtils.correctItemQuantity(req, attributeName);
        return "jsp/edit_order.jsp";
    }
}
