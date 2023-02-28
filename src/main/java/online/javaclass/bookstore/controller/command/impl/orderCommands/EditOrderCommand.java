package online.javaclass.bookstore.controller.command.impl.orderCommands;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.OrderService;
import online.javaclass.bookstore.service.dto.OrderDto;
import online.javaclass.bookstore.service.dto.OrderItemDto;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller("edit_order")
public class EditOrderCommand implements Command {
    private final OrderService orderService;

    @Override
    public String execute(HttpServletRequest req) {
        String attributeName = "items";
        HttpSession session = req.getSession();
        OrderDto order = OrderCommandUtils.setOrderParameters(req, attributeName);
        Long orderId = Long.parseLong(req.getParameter("id"));
        order.setId(orderId);
        for (OrderItemDto item : order.getItems()) {
            item.setOrderId(orderId);
        }
        OrderDto updatedOrder = orderService.update(order);
        req.setAttribute("order_id", updatedOrder.getId());
        session.removeAttribute(attributeName);
        return "jsp/successful_order.jsp";
    }
}

