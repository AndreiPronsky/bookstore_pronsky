package online.javaclass.bookstore.controller.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.dto.BookDto;

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
        Map<BookDto, Integer> cartItemMap = (Map)session.getAttribute("cart");
        BigDecimal cost = BigDecimal.ZERO;
        for (Map.Entry<BookDto, Integer> entry : cartItemMap.entrySet()) {
            cost = cost.add(entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())));
        }
        session.setAttribute("cost", cost);
        if (session.getAttribute("user") == null) {
            return "jsp/login.jsp";
        }
        return "jsp/confirm_order.jsp";
    }
}
