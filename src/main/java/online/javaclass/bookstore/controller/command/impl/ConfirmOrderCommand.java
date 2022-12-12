package online.javaclass.bookstore.controller.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.OrderService;
import online.javaclass.bookstore.service.dto.CartItemDto;
import online.javaclass.bookstore.service.dto.OrderDto;
import online.javaclass.bookstore.service.dto.OrderItemDto;
import online.javaclass.bookstore.service.dto.UserDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ConfirmOrderCommand implements Command {
    private final OrderService orderService;
    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        OrderDto order = setOrderParameters(req, session);
        List<OrderItemDto> items = extractOrderItems(session);
        order.setItems(items);
        OrderDto createdOrder = orderService.create(order);
        for (OrderItemDto item : items) {
            item.setOrderId(createdOrder.getId());
        }
        createdOrder.setItems(items);
        orderService.update(createdOrder);
        session.setAttribute("order_id", createdOrder.getId());
        return "jsp/successful_order.jsp";
    }

    private static OrderDto setOrderParameters(HttpServletRequest req, HttpSession session) {
        OrderDto order = new OrderDto();
        UserDto user = (UserDto) session.getAttribute("user");
        order.setDeliveryType(OrderDto.DeliveryType.valueOf(req.getParameter("delivery_type")));
        order.setOrderStatus(OrderDto.OrderStatus.OPEN);
        order.setPaymentMethod(OrderDto.PaymentMethod.valueOf(req.getParameter("payment_method")));
        order.setPaymentStatus(OrderDto.PaymentStatus.UNPAID);
        order.setCost(BigDecimal.valueOf(Double.parseDouble(session.getAttribute("cost").toString())));
        order.setUser(user);
        return order;
    }

    private List<OrderItemDto> extractOrderItems(HttpSession session) {
        Map<Long, CartItemDto> cartItemDtoMap = (Map)session.getAttribute("cart");
        List<OrderItemDto> items = new ArrayList<>();
        for (Map.Entry<Long, CartItemDto> entry : cartItemDtoMap.entrySet()) {
            OrderItemDto item = new OrderItemDto();
            item.setPrice(entry.getValue().getPrice());
            item.setQuantity(entry.getValue().getQuantity());
            item.setBookId(entry.getValue().getId());
            items.add(item);
        }
        return items;
    }
}
