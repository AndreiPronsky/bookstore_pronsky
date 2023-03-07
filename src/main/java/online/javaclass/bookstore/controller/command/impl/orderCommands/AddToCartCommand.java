package online.javaclass.bookstore.controller.command.impl.orderCommands;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.FrontController;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller("add_to_cart")
public class AddToCartCommand implements Command {

    private final BookService bookService;

    @Override
    public String execute(HttpServletRequest req) {
        Long bookId = Long.parseLong(req.getParameter("id"));
        HttpSession session = req.getSession();
        Map<BookDto, Integer> cartItems = (Map) session.getAttribute("cart");
        if (cartItems == null) {
            cartItems = new HashMap<>();
            session.setAttribute("cart", cartItems);
        }
        BookDto book = bookService.getById(bookId);
        if (cartItems.containsKey(book)) {
            cartItems.put(book, cartItems.get(book) + 1);

        } else {
            cartItems.put(book, 1);
        }
        String rawPageNumber = req.getParameter("page");
        long pageNumber = 1L;
        if (rawPageNumber != null) {
            pageNumber = Long.parseLong(rawPageNumber);
        }
        String page = "controller?command=books&page=" + pageNumber;
        return FrontController.REDIRECT + page;
    }
}