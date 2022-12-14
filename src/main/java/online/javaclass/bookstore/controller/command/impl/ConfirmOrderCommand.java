package online.javaclass.bookstore.controller.command.impl;

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
public class ConfirmOrderCommand implements Command {
    private final OrderService orderService;
    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        OrderDto order = setOrderParameters(req, session);
        OrderDto createdOrder = orderService.create(order);
        req.setAttribute("order_id", createdOrder.getId());
        return "jsp/successful_order.jsp";
    }

    private OrderDto setOrderParameters(HttpServletRequest req, HttpSession session) {
        OrderDto order = new OrderDto();
        order.setDeliveryType(OrderDto.DeliveryType.valueOf(req.getParameter("delivery_type")));
        order.setOrderStatus(OrderDto.OrderStatus.OPEN);
        order.setPaymentMethod(OrderDto.PaymentMethod.valueOf(req.getParameter("payment_method")));
        order.setPaymentStatus(OrderDto.PaymentStatus.UNPAID);
        order.setCost(BigDecimal.valueOf(Double.parseDouble(session.getAttribute("cost").toString())));
        order.setItems(listItems(session));
        UserDto user = (UserDto) session.getAttribute("user");
        order.setUser(user);
        return order;
    }

    private List<OrderItemDto> listItems(HttpSession session) {
        Map<BookDto, Integer> itemMap= (Map)session.getAttribute("cart");
        List<OrderItemDto> items = new ArrayList<>();
        for (Map.Entry<BookDto, Integer> entry : itemMap.entrySet()) {
            OrderItemDto itemDto = new OrderItemDto();
            itemDto.setQuantity(entry.getValue());
            itemDto.setBookId(entry.getKey().getId());
            itemDto.setPrice(entry.getKey().getPrice());
            items.add(itemDto);
        }
        return items;
    }
}
