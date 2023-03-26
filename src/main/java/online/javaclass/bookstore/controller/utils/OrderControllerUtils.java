package online.javaclass.bookstore.controller.utils;

import online.javaclass.bookstore.service.dto.BookDto;
import online.javaclass.bookstore.service.dto.OrderDto;
import online.javaclass.bookstore.service.dto.OrderItemDto;
import online.javaclass.bookstore.service.dto.UserDto;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class OrderControllerUtils {
    public OrderControllerUtils() {
    }

    public OrderDto setOrderParameters(HttpServletRequest req, String attributeName) {
        HttpSession session = req.getSession();
        OrderDto order = new OrderDto();
        String rawId = req.getParameter("id");
        Long id = null;
        if (rawId != null) {
            id = Long.parseLong(rawId);
        }
        order.setId(id);
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

    private BigDecimal calculateCost(HttpSession session, String attributeName) {
        Map<BookDto, Integer> cartItemMap = (Map) session.getAttribute(attributeName);
        BigDecimal cost = BigDecimal.ZERO;
        for (Map.Entry<BookDto, Integer> entry : cartItemMap.entrySet()) {
            cost = cost.add(entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())));
        }
        return cost;
    }

    private List<OrderItemDto> listItems(HttpSession session, String attributeName) {
        Map<BookDto, Integer> itemMap = (Map) session.getAttribute(attributeName);
        List<OrderItemDto> items = new ArrayList<>();
        for (Map.Entry<BookDto, Integer> entry : itemMap.entrySet()) {
            OrderItemDto itemDto = new OrderItemDto();
            itemDto.setQuantity(entry.getValue());
            itemDto.setBook(entry.getKey());
            itemDto.setPrice(entry.getKey().getPrice());
            items.add(itemDto);
        }
        return items;
    }

    public void correctItemQuantity(HttpServletRequest req, String attributeName) {
        long id = Long.parseLong(req.getParameter("id"));
        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        Map<BookDto, Integer> items = (Map) session.getAttribute(attributeName);
        for (Map.Entry<BookDto, Integer> item : items.entrySet()) {
            Long itemId = item.getKey().getId();
            Integer quantity = item.getValue();
            if (itemId == id) {
                if (action.equals("dec") && quantity > 1) {
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
