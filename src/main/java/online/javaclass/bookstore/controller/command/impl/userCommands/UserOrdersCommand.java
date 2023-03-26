package online.javaclass.bookstore.controller.command.impl.userCommands;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.exceptions.AppException;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.OrderService;
import online.javaclass.bookstore.service.dto.BookDto;
import online.javaclass.bookstore.service.dto.OrderDto;
import online.javaclass.bookstore.service.dto.OrderItemDto;
import online.javaclass.bookstore.service.dto.UserDto;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller("my_orders")
public class UserOrdersCommand implements Command {
    private final OrderService orderService;
    private final BookService bookService;

    @LogInvocation
    @Override
    public String execute(HttpServletRequest req) {
        try {
            HttpSession session = req.getSession();
            UserDto user = (UserDto) session.getAttribute("user");
            Long userId = user.getId();
            Map<OrderDto, Map<OrderItemDto, BookDto>> orders = mapOrdersByUserId(userId);
            req.setAttribute("orders", orders);
            return "jsp/my_orders.jsp";
        } catch (AppException e) {
            return "jsp/error.jsp";
        }
    }

    private Map<OrderDto, Map<OrderItemDto, BookDto>> mapOrdersByUserId(Long userId) {
        Map<OrderDto, Map<OrderItemDto, BookDto>> orders = new HashMap<>();
        List<OrderDto> userOrders = orderService.getOrdersByUserId(userId);
        for (OrderDto order : userOrders) {
            Map<OrderItemDto, BookDto> itemBookMap = new HashMap<>();
            for (OrderItemDto item : order.getItems()) {
                itemBookMap.put(item, bookService.getById(item.getBook().getId()));
            }
            orders.put(order, itemBookMap);
        }
        return orders;
    }
}
