package online.javaclass.bookstore.controller.command.impl.orderCommands;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.OrderService;
import online.javaclass.bookstore.service.dto.OrderDto;

@RequiredArgsConstructor
public class OrderCommand implements Command {
    private final OrderService orderService;
    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        OrderDto order = orderService.getById(id);
        req.setAttribute("order", order);
        return "jsp/order.jsp";
    }
}
