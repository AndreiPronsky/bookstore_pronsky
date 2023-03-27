package online.javaclass.bookstore.controller;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.dto.BookDto;
import online.javaclass.bookstore.service.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    @LogInvocation
    @RequestMapping("/add")
    public String addToCart(HttpSession session, @ModelAttribute BookDto book, @RequestParam Long page) {
        Map<BookDto, Integer> cart = (Map) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }
        if (cart.containsKey(book)) {
            cart.put(book, cart.get(book) + 1);
        } else {
            cart.put(book, 1);
        }
        return "redirect: /books/page=" + page;
    }

    @LogInvocation
    @RequestMapping("")
    public String cart(@SessionAttribute UserDto user) {
        if (user == null) {
            return "login";
        }
        return "confirm_order";
    }

    @LogInvocation
    @RequestMapping("/edit")
    public String edit(@RequestParam Long id, @RequestParam String action,
                       @SessionAttribute Map<BookDto, Integer> cart) {
        correctItemQuantity(id, action, cart);
        return "redirect: cart";
    }

    private void correctItemQuantity(Long id, String action, Map<BookDto, Integer> cart) {
        for (Map.Entry<BookDto, Integer> item : cart.entrySet()) {
            Long itemId = item.getKey().getId();
            Integer quantity = item.getValue();
            if (itemId.equals(id)) {
                if (action.equals("dec") && quantity > 1) {
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
    }
}
