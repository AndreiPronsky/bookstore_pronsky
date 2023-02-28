package online.javaclass.bookstore.controller.command.impl.orderCommands;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.controller.FrontController;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.OrderService;
import online.javaclass.bookstore.service.dto.BookDto;
import online.javaclass.bookstore.service.dto.OrderDto;
import online.javaclass.bookstore.service.dto.UserDto;
import online.javaclass.bookstore.service.dto.OrderItemDto;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Controller("edit_order_form")
public class EditOrderFormCommand implements Command {
    private final OrderService orderService;
    private final BookService bookService;

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        UserDto user = (UserDto) session.getAttribute("user");
        OrderDto order = orderService.getById(Long.parseLong(req.getParameter("id")));
        if (session.getAttribute("user") == null || notMatchingUserIgnoreAdmin(user, order)) {
            return FrontController.REDIRECT + "index.jsp";
        } else if (notAdminRole(user) && notOpenStatus(order)) {
            return FrontController.REDIRECT + "index.jsp";
        }
        Map<BookDto, Integer> itemMap = new HashMap<>();
        for (OrderItemDto item : order.getItems()) {
            BookDto book = bookService.getById(item.getBookId());
            itemMap.put(book, item.getQuantity());
        }
        session.setAttribute("items", itemMap);
        session.setAttribute("order", order);
        return "jsp/edit_order.jsp";
    }

    private boolean notMatchingUserIgnoreAdmin(UserDto user, OrderDto order) {
        return (!(user.getId().equals(order.getUser().getId())) && !(user.getRole().equals(UserDto.Role.ADMIN)));
    }

    private boolean notAdminRole(UserDto user) {
        return !user.getRole().equals(UserDto.Role.ADMIN);
    }

    private boolean notOpenStatus(OrderDto order) {
        return !order.getOrderStatus().equals(OrderDto.OrderStatus.OPEN);
    }
}
