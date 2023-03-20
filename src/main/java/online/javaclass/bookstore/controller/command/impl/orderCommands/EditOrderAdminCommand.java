package online.javaclass.bookstore.controller.command.impl.orderCommands;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.controller.FrontController;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.OrderService;
import online.javaclass.bookstore.service.dto.OrderDto;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller("edit_order_admin")
public class EditOrderAdminCommand implements Command {
    private final OrderService orderService;

    @LogInvocation
    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Long orderId = ((OrderDto) session.getAttribute("order")).getId();
        OrderDto order = orderService.getById(orderId);
        order.setDeliveryType(OrderDto.DeliveryType.valueOf(req.getParameter("delivery_type")));
        order.setOrderStatus(OrderDto.OrderStatus.valueOf(req.getParameter("order_status")));
        order.setPaymentMethod(OrderDto.PaymentMethod.valueOf(req.getParameter("payment_method")));
        order.setPaymentStatus(OrderDto.PaymentStatus.valueOf(req.getParameter("payment_status")));
        OrderDto updatedOrder = orderService.update(order);
        req.setAttribute("order", updatedOrder);
        String page = "controller?command=order&id=" + updatedOrder.getId();
        return FrontController.REDIRECT + page;
    }
}
