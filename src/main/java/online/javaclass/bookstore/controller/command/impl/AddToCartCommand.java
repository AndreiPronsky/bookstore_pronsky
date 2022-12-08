package online.javaclass.bookstore.controller.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.BookService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class AddToCartCommand implements Command {
    private final BookService bookService;
    Map<String, Integer> cart;
    BigDecimal totalCost;

    @Override
    public String execute(HttpServletRequest req) {

        String title = bookService.getById(Long.parseLong(req.getParameter("id"))).getTitle();
        HttpSession session = req.getSession();

        if (session.getAttribute("cart") == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }
        if (cart.containsKey(title)) {
            cart.put(title, cart.get(title) + 1);
        } else {
            cart.put(title, 1);
        }
        return "jsp/cart.jsp";
    }
}
