package online.javaclass.bookstore.controller.command.impl.orderCommands;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.OrderService;
import online.javaclass.bookstore.service.dto.OrderDto;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller("search_orders")
public class SearchOrdersCommand implements Command {
    private final OrderService orderService;

    @Override
    public String execute(HttpServletRequest req) {
        String input = req.getParameter("search");
        Long orderId = Long.parseLong(input);
        OrderDto order = orderService.getById(orderId);
        req.setAttribute("order", order);
        return "jsp/order.jsp";
    }
}
