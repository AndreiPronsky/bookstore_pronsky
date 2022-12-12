package online.javaclass.bookstore.controller.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.dto.CartItemDto;

import java.math.BigDecimal;
import java.util.Map;

@RequiredArgsConstructor
public class CartCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        if (session.getAttribute("cart") == null) {
            return "jsp/cart.jsp";
        }
        Map<Long, CartItemDto> cartItemMap = (Map) session.getAttribute("cart");
        BigDecimal cost = BigDecimal.ZERO;
        for (Map.Entry<Long, CartItemDto> entry : cartItemMap.entrySet()) {
            CartItemDto item = entry.getValue();
            cost = cost.add(entry.getValue().getPrice().multiply(BigDecimal.valueOf(entry.getValue().getQuantity())));
            cartItemMap.put(item.getId(), item);
        }
        session.setAttribute("cart", cartItemMap);
        session.setAttribute("cost", cost);
        return "jsp/confirm_order.jsp";
    }
}
