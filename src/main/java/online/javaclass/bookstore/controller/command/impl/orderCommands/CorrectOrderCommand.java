package online.javaclass.bookstore.controller.command.impl.orderCommands;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.controller.utils.OrderControllerUtils;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

@Controller("corr_order")
@RequiredArgsConstructor
public class CorrectOrderCommand implements Command {

    private final OrderControllerUtils orderControllerUtils;

    @LogInvocation
    @Override
    public String execute(HttpServletRequest req) {
        String attributeName = "items";
        orderControllerUtils.correctItemQuantity(req, attributeName);
        return "jsp/edit_order.jsp";
    }
}
