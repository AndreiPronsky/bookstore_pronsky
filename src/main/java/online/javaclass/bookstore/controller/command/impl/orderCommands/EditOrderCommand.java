package online.javaclass.bookstore.controller.command.impl.orderCommands;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.OrderService;
import online.javaclass.bookstore.service.dto.BookDto;
import online.javaclass.bookstore.service.dto.OrderDto;
import online.javaclass.bookstore.service.dto.OrderItemDto;
import online.javaclass.bookstore.service.dto.UserDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class EditOrderCommand implements Command {
    private final OrderService orderService;

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        OrderDto order = setOrderParameters(req, session);
        OrderDto updatedOrder = orderService.update(order);
        req.setAttribute("order_id", updatedOrder.getId());
        session.removeAttribute("items");
        return "jsp/successful_order.jsp";
    }

    private OrderDto setOrderParameters(HttpServletRequest req, HttpSession session) {
        OrderDto order = new OrderDto();
        Long orderId = Long.parseLong(req.getParameter("id"));
        order.setId(orderId);
        order.setDeliveryType(OrderDto.DeliveryType.valueOf(req.getParameter("delivery_type")));
        order.setOrderStatus(OrderDto.OrderStatus.OPEN);
        order.setPaymentMethod(OrderDto.PaymentMethod.valueOf(req.getParameter("payment_method")));
        order.setPaymentStatus(OrderDto.PaymentStatus.UNPAID);
        order.setCost(calculateCost(session));
        order.setItems(listItems(session, orderId));
        UserDto user = (UserDto) session.getAttribute("user");
        order.setUser(user);
        return order;
    }

    private List<OrderItemDto> listItems(HttpSession session, Long orderId) {
        Map<BookDto, Integer> itemMap = (Map) session.getAttribute("items");
        List<OrderItemDto> items = new ArrayList<>();
        for (Map.Entry<BookDto, Integer> entry : itemMap.entrySet()) {
            OrderItemDto itemDto = new OrderItemDto();
            itemDto.setOrderId(orderId);
            itemDto.setQuantity(entry.getValue());
            itemDto.setBookId(entry.getKey().getId());
            itemDto.setPrice(entry.getKey().getPrice());
            items.add(itemDto);
        }
        return items;
    }

    private BigDecimal calculateCost(HttpSession session) {
        Map<BookDto, Integer> cartItemMap = (Map) session.getAttribute("items");
        BigDecimal cost = BigDecimal.ZERO;
        for (Map.Entry<BookDto, Integer> entry : cartItemMap.entrySet()) {
            cost = cost.add(entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())));
        }
        return cost;
    }


//        OrderDto order = new OrderDto();
//        order.setId(Long.parseLong(req.getParameter("id")));
//        OrderDto oldOrder = orderService.getById(Long.parseLong(req.getParameter("id")));
//        order.setUser(oldOrder.getUser());
//        if (req.getParameter("order_status") == null & req.getParameter("payment_status") == null) {
//            order.setOrderStatus(oldOrder.getOrderStatus());
//            order.setPaymentStatus(oldOrder.getPaymentStatus());
//        } else {
//            order.setOrderStatus(OrderDto.OrderStatus.valueOf(req.getParameter("order_status")));
//            order.setPaymentStatus(OrderDto.PaymentStatus.valueOf(req.getParameter("payment_status")));
//        }
//        order.setPaymentMethod(OrderDto.PaymentMethod.valueOf(req.getParameter("payment_method")));
//        order.setDeliveryType(OrderDto.DeliveryType.valueOf(req.getParameter("delivery_type")));
//        List<OrderItemDto> items = mapItems(req);
//        order.setCost(calculateCost(items));
//        order.setItems(items);
//        orderService.update(order);
//        return "jsp/my_orders.jsp";
}

