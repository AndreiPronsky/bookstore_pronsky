package online.javaclass.bookstore.web.controller;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;
import online.javaclass.bookstore.service.dto.UserDto;
import online.javaclass.bookstore.web.filter.SecurityCheck;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    private final BookService bookService;

    @LogInvocation
    @GetMapping("/add")
    public String addToCart(HttpSession session, @RequestParam Long id, @RequestParam String page, @RequestParam String page_size) {
        Map<BookDto, Integer> cart = (Map) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }
        BookDto book = bookService.getById(id);
        if (cart.containsKey(book)) {
            cart.put(book, cart.get(book) + 1);
        } else {
            cart.put(book, 1);
        }
        return "redirect:/books/all?page=" + page + "&page_size=" + page_size;
    }

    @LogInvocation
    @RequestMapping("")
    public String cart(HttpSession session, Model model) {
        Map<BookDto, Integer> cart = (Map) session.getAttribute("cart");
        model.addAttribute("cart", cart);
        return "/cart";
    }

    @LogInvocation
    @RequestMapping("/edit")
    @SecurityCheck(allowed = {UserDto.Role.USER})
    public String edit(@RequestParam Long id, @RequestParam String action,
                       @SessionAttribute Map<BookDto, Integer> cart) {
        correctItemQuantity(id, action, cart);
        return "redirect:/cart";
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
