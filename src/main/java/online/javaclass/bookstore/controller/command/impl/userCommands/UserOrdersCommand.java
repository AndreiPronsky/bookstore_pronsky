package online.javaclass.bookstore.controller.command.impl.userCommands;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.OrderService;
import online.javaclass.bookstore.service.dto.BookDto;
import online.javaclass.bookstore.service.dto.OrderDto;
import online.javaclass.bookstore.service.dto.OrderItemDto;
import online.javaclass.bookstore.service.dto.UserDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Log4j2
public class UserOrdersCommand implements Command {
    private final OrderService orderService;
    private final BookService bookService;

    @Override
    public String execute(HttpServletRequest req) {
        try {
            HttpSession session = req.getSession();
            UserDto user = (UserDto) session.getAttribute("user");
            Long userId = user.getId();
            Map<OrderDto, Map<OrderItemDto, BookDto>> orders = new HashMap<>();
            List<OrderDto> userOrders = orderService.getOrdersByUserId(userId);
            for (OrderDto order : userOrders) {
                Map<OrderItemDto, BookDto> itemBookMap = new HashMap<>();
                for (OrderItemDto item : order.getItems()) {
                    itemBookMap.put(item, bookService.getById(item.getBookId()));
                }
                orders.put(order, itemBookMap);
            }
            req.setAttribute("orders", orders);
            return "jsp/my_orders.jsp";
        } catch (Exception e) {
            log.error(e.getClass() + " " + e.getMessage());
            return "jsp/error.jsp";
        }
    }
}