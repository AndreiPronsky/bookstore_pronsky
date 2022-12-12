package online.javaclass.bookstore.controller.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;
import online.javaclass.bookstore.service.dto.CartItemDto;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class AddToCartCommand implements Command {
    Map<Long, CartItemDto> cartItems;
    private final BookService bookService;

    @Override
    public String execute(HttpServletRequest req) {
        Long bookId = Long.parseLong(req.getParameter("id"));
        HttpSession session = req.getSession();

        if (session.getAttribute("cart") == null) {
            cartItems = new HashMap<>();
            session.setAttribute("cart", cartItems);
        }
        BookDto book = bookService.getById(bookId);
        CartItemDto item;
        if (cartItems.containsKey(bookId)) {
            item = cartItems.get(bookId);
            item.setQuantity(item.getQuantity() + 1);

        } else {
            item = new CartItemDto();
            item.setQuantity(1);
            setParameters(item, book);
            cartItems.put(bookId, item);
        }
        return "jsp/books.jsp";
    }

    private void setParameters(CartItemDto item, BookDto book) {
        item.setTitle(book.getTitle());
        item.setAuthor(book.getAuthor());
        item.setIsbn(book.getIsbn());
        item.setGenre(book.getGenre());
        item.setCover(book.getCover());
        item.setPages(book.getPages());
        item.setRating(book.getRating());
        item.setPrice(book.getPrice());
    }
}
