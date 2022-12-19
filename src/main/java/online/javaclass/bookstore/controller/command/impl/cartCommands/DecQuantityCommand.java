package online.javaclass.bookstore.controller.command.impl.cartCommands;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.dto.BookDto;

import java.util.Map;

public class DecQuantityCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        long id = Long.parseLong(req.getParameter("id"));
        HttpSession session = req.getSession();
        Map<BookDto, Integer> cart = (Map)session.getAttribute("cart");
        for (Map.Entry<BookDto, Integer> item : cart.entrySet()) {
            Long itemId = item.getKey().getId();
            if (itemId == id) {
                Integer quantity = item.getValue();
                cart.put(item.getKey(), quantity - 1);
            }
        }
        return "jsp/confirm_order.jsp";
    }
}
