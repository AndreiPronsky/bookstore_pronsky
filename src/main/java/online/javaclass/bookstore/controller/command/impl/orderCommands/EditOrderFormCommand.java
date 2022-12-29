package online.javaclass.bookstore.controller.command.impl.orderCommands;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.OrderService;
import online.javaclass.bookstore.service.dto.BookDto;
import online.javaclass.bookstore.service.dto.OrderDto;
import online.javaclass.bookstore.service.dto.OrderItemDto;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class EditOrderFormCommand implements Command {
    private final OrderService orderService;
    private final BookService bookService;

    @Override
    public String execute(HttpServletRequest req) {
        Long orderId = Long.parseLong(req.getParameter("id"));
        HttpSession session = req.getSession();
        OrderDto order = orderService.getById(orderId);
        Map<BookDto, Integer> itemMap = new HashMap<>();
        for (OrderItemDto item : order.getItems()) {
            BookDto book = bookService.getById(item.getBookId());
            itemMap.put(book, item.getQuantity());
        }
        session.setAttribute("items", itemMap);
        session.setAttribute("order", order);
        return "jsp/edit_order.jsp";
    }
}
