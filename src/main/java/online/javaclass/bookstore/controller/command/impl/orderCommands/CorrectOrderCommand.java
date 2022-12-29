package online.javaclass.bookstore.controller.command.impl.orderCommands;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.dto.BookDto;

import java.util.Map;

public class CorrectOrderCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        long id = Long.parseLong(req.getParameter("id"));
        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        Map<BookDto, Integer> items = (Map) session.getAttribute("items");
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
        return "jsp/edit_order.jsp";
    }
}
