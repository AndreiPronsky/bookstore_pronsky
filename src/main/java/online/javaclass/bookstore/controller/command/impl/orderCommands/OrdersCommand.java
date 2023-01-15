package online.javaclass.bookstore.controller.command.impl.orderCommands;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.controller.PagingUtil;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.OrderService;
import online.javaclass.bookstore.service.UserService;
import online.javaclass.bookstore.service.dto.OrderDto;
import online.javaclass.bookstore.service.dto.PageableDto;
import online.javaclass.bookstore.service.dto.UserDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class OrdersCommand implements Command {
    private final OrderService orderService;
    private final UserService userService;

    @Override
    public String execute(HttpServletRequest req) {
        PageableDto pageable = PagingUtil.getPageable(req);
        List<OrderDto> orders = orderService.getAll(pageable);
            Map<OrderDto, UserDto> ordersMap = mapOrders(orders);
            req.setAttribute("page", pageable.getPage());
            req.setAttribute("total_pages", pageable.getTotalPages());
            req.setAttribute("orders", ordersMap);
            return "jsp/orders.jsp";
    }

    private Map<OrderDto, UserDto> mapOrders(List<OrderDto> orders) {
        Map<OrderDto, UserDto> orderMap = new HashMap<>();
        for (OrderDto order : orders) {
            UserDto user = userService.getById(order.getUser().getId());
            orderMap.put(order, user);
        }
        return orderMap;
    }
}
