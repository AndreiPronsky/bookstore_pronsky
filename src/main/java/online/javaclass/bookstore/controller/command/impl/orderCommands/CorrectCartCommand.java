package online.javaclass.bookstore.controller.command.impl.orderCommands;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.dto.BookDto;

import java.util.Map;

public class CorrectCartCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        long id = Long.parseLong(req.getParameter("id"));
        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        Map<BookDto, Integer> cart = (Map) session.getAttribute("cart");
        for (Map.Entry<BookDto, Integer> item : cart.entrySet()) {
            Long itemId = item.getKey().getId();
            Integer quantity = item.getValue();
            if (itemId == id) {
                if (action.equals("dec")) {
                    cart.put(item.getKey(), quantity - 1);
                    break;
                }
                if (action.equals("inc")) {
                    cart.put(item.getKey(), quantity + 1);
                    break;
                }
                if (action.equals("remove")) {
                    cart.remove(item.getKey());
                    break;
                }
            }
        }
        return "jsp/confirm_order.jsp";
    }
}
