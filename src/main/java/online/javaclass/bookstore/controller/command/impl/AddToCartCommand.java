package online.javaclass.bookstore.controller.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.command.Command;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class AddToCartCommand implements Command {
    Map<Long, Integer> bookIdAndQuantityMap;

    @Override
    public String execute(HttpServletRequest req) {

        Long bookId = Long.parseLong(req.getParameter("id"));
        HttpSession session = req.getSession();

        if (session.getAttribute("cart") == null) {
            bookIdAndQuantityMap = new HashMap<>();
            session.setAttribute("cart", bookIdAndQuantityMap);
        }
        if (bookIdAndQuantityMap.containsKey(bookId)) {
            bookIdAndQuantityMap.put(bookId, bookIdAndQuantityMap.get(bookId) + 1);
        } else {
            bookIdAndQuantityMap.put(bookId, 1);
        }
        return "jsp/cart.jsp";
    }
}
