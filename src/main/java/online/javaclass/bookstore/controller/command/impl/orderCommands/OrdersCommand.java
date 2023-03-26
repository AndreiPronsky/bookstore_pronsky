package online.javaclass.bookstore.controller.command.impl.orderCommands;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.PagingUtil;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.OrderService;
import online.javaclass.bookstore.service.UserService;
import online.javaclass.bookstore.service.dto.OrderDto;
import online.javaclass.bookstore.service.dto.PageableDto;
import online.javaclass.bookstore.service.dto.UserDto;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller("orders")
public class OrdersCommand implements Command {
    private final OrderService orderService;
    private final UserService userService;
    private final PagingUtil pagingUtil;

    @LogInvocation
    @Override
    public String execute(HttpServletRequest req) {
        PageableDto pageable = pagingUtil.getPageable(req);
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
