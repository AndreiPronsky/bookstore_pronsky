package online.javaclass.bookstore.controller.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;

import java.util.HashMap;
import java.util.Map;
@RequiredArgsConstructor
public class CartCommand implements Command {
    private final BookService bookService;
    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Map<Long, Integer> bookIdAndQuantityMap = (Map)session.getAttribute("cart");
        Map<Long, BookDto> bookDtoMap = new HashMap<>();
        for (Map.Entry<Long, Integer> entry : bookIdAndQuantityMap.entrySet()) {
            bookDtoMap.put(entry.getKey(), bookService.getById(entry.getKey()));
        }
        req.setAttribute("book_map", bookDtoMap);
        return "jsp/cart.jsp";
    }
}
