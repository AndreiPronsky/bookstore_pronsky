package online.javaclass.bookstore.controller.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.data.entities.OrderItem;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.OrderService;
import online.javaclass.bookstore.service.dto.OrderDto;

import online.javaclass.bookstore.service.dto.UserDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class CartCommand implements Command {
    private final OrderService orderService;
    private final BookService bookService;
    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        OrderDto order = new OrderDto();
        order.setUser((UserDto)session.getAttribute("user"));
        order.setOrderStatus(OrderDto.OrderStatus.OPEN);
        order.setPaymentMethod(OrderDto.PaymentMethod.valueOf(session.getAttribute("paymentMethod").toString()));
        order.setPaymentStatus(OrderDto.PaymentStatus.UNPAID);
        order.setDeliveryType(OrderDto.DeliveryType.valueOf(session.getAttribute("deliveryType").toString()));
        OrderDto invalidOrder = orderService.create(order);
        Map<Long, Integer> orderItemsMap = (Map)session.getAttribute("cart");
        List<OrderItem> orderItems = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : orderItemsMap.entrySet()) {
            OrderItem item = new OrderItem();
            item.setBookId(entry.getKey());
            item.setPrice(bookService.getById(entry.getKey()).getPrice());
            item.setQuantity(entry.getValue());
            item.setOrderId(invalidOrder.getId());
            orderItems.add(item);
        }
        BigDecimal cost = BigDecimal.ZERO;
        for (OrderItem item : orderItems) {
            cost = cost.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        invalidOrder.setItems(orderItems);
        invalidOrder.setCost(cost);
        OrderDto validOrder = orderService.update(invalidOrder);
        session.setAttribute("order", validOrder);
        return "jsp/successful_order.jsp";
    }
}
