package online.javaclass.bookstore.controller.command.impl.orderCommands;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.OrderService;
import online.javaclass.bookstore.service.dto.OrderDto;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller("order")
public class OrderCommand implements Command {
    private final OrderService orderService;

    @LogInvocation
    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        OrderDto order = orderService.getById(id);
        req.setAttribute("order", order);
        return "jsp/order.jsp";
    }
}
