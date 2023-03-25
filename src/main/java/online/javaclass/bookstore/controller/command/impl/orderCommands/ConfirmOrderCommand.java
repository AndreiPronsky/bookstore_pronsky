package online.javaclass.bookstore.controller.command.impl.orderCommands;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.OrderService;
import online.javaclass.bookstore.service.dto.OrderDto;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller("confirm_order")
public class ConfirmOrderCommand implements Command {
    private final OrderService orderService;

    @LogInvocation
    @Override
    public String execute(HttpServletRequest req) {
        String attributeName = "cart";
        HttpSession session = req.getSession();
        OrderDto order = OrderCommandUtils.setOrderParameters(req, attributeName);
        OrderDto createdOrder = orderService.create(order);
        req.setAttribute("order_id", createdOrder.getId());
        session.removeAttribute(attributeName);
        return "jsp/successful_order.jsp";
    }
}
