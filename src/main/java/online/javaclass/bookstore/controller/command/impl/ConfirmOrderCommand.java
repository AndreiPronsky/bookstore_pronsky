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
public class ConfirmOrderCommand implements Command {
    private final OrderService orderService;
    private final BookService bookService;
    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        OrderDto invalidOrder = createOrderWithoutItems(session);
        Map<Long, Integer> bookIdAndQuantityMap = (Map)session.getAttribute("cart");
        List<OrderItem> orderItems = createItemList(invalidOrder.getId(), bookIdAndQuantityMap);
        BigDecimal cost = BigDecimal.ZERO;
        for (OrderItem item : orderItems) {
            cost = cost.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        updateOrderToValidState(invalidOrder, orderItems, cost);
        session.setAttribute("cost", cost);
        return "jsp/successful_order.jsp";
    }

    private void updateOrderToValidState(OrderDto invalidOrder, List<OrderItem> orderItems, BigDecimal cost) {
        invalidOrder.setItems(orderItems);
        invalidOrder.setCost(cost);
        orderService.update(invalidOrder);
    }

    private List<OrderItem> createItemList(Long orderId, Map<Long, Integer> bookIdAndQuantityMap) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : bookIdAndQuantityMap.entrySet()) {
            OrderItem item = new OrderItem();
            item.setBookId(entry.getKey());
            item.setPrice(bookService.getById(entry.getKey()).getPrice());
            item.setQuantity(entry.getValue());
            item.setOrderId(orderId);
            orderItems.add(item);
        }
        return orderItems;
    }

    private OrderDto createOrderWithoutItems(HttpSession session) {
        OrderDto order = new OrderDto();
        order.setUser((UserDto) session.getAttribute("user"));
        order.setOrderStatus(OrderDto.OrderStatus.OPEN);
        order.setPaymentMethod(OrderDto.PaymentMethod.valueOf(session.getAttribute("paymentMethod").toString()));
        order.setPaymentStatus(OrderDto.PaymentStatus.UNPAID);
        order.setDeliveryType(OrderDto.DeliveryType.valueOf(session.getAttribute("deliveryType").toString()));
        return orderService.create(order);
    }
}
