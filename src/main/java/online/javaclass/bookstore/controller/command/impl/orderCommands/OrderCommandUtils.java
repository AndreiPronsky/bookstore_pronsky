package online.javaclass.bookstore.controller.command.impl.orderCommands;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import online.javaclass.bookstore.service.dto.BookDto;
import online.javaclass.bookstore.service.dto.OrderDto;
import online.javaclass.bookstore.service.dto.OrderItemDto;
import online.javaclass.bookstore.service.dto.UserDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderCommandUtils {
    static OrderDto setOrderParameters(HttpServletRequest req, String attributeName) {
        HttpSession session = req.getSession();
        OrderDto order = new OrderDto();
        order.setDeliveryType(OrderDto.DeliveryType.valueOf(req.getParameter("delivery_type")));
        order.setOrderStatus(OrderDto.OrderStatus.OPEN);
        order.setPaymentMethod(OrderDto.PaymentMethod.valueOf(req.getParameter("payment_method")));
        order.setPaymentStatus(OrderDto.PaymentStatus.UNPAID);
        order.setCost(calculateCost(session, attributeName));
        order.setItems(listItems(session, attributeName));
        UserDto user = (UserDto) session.getAttribute("user");
        order.setUser(user);
        return order;
    }

    private static BigDecimal calculateCost(HttpSession session, String attributeName) {
        Map<BookDto, Integer> cartItemMap = (Map) session.getAttribute(attributeName);
        BigDecimal cost = BigDecimal.ZERO;
        for (Map.Entry<BookDto, Integer> entry : cartItemMap.entrySet()) {
            cost = cost.add(entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())));
        }
        return cost;
    }

    private static List<OrderItemDto> listItems(HttpSession session, String attributeName) {
        Map<BookDto, Integer> itemMap = (Map) session.getAttribute(attributeName);
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

    static void correctItemQuantity(HttpServletRequest req, String attributeName) {
        long id = Long.parseLong(req.getParameter("id"));
        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        Map<BookDto, Integer> items = (Map) session.getAttribute(attributeName);
        for (Map.Entry<BookDto, Integer> item : items.entrySet()) {
            Long itemId = item.getKey().getId();
            Integer quantity = item.getValue();
            if (itemId == id) {
                if (action.equals("dec")) {
                    items.put(item.getKey(), quantity - 1);
                    break;
                }
                if (action.equals("inc")) {
                    items.put(item.getKey(), quantity + 1);
                    break;
                }
                if (action.equals("remove")) {
                    items.remove(item.getKey());
                    break;
                }
            }
        }
    }
}
