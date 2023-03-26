package online.javaclass.bookstore.controller.command.impl.orderCommands;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.controller.utils.OrderControllerUtils;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.OrderService;
import online.javaclass.bookstore.service.dto.OrderDto;
import online.javaclass.bookstore.service.dto.OrderItemDto;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller("edit_order")
public class EditOrderCommand implements Command {
    private final OrderService orderService;

    private final OrderControllerUtils orderControllerUtils;

    @LogInvocation
    @Override
    public String execute(HttpServletRequest req) {
        String attributeName = "items";
        HttpSession session = req.getSession();
        OrderDto order = orderControllerUtils.setOrderParameters(req, attributeName);
        for (OrderItemDto item : order.getItems()) {
            item.setOrderId(order.getId());
        }
        OrderDto updatedOrder = orderService.update(order);
        req.setAttribute("order_id", updatedOrder.getId());
        session.removeAttribute(attributeName);
        return "jsp/successful_order.jsp";
    }
}

