package online.javaclass.bookstore.web.controller.view;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;
import org.springframework.data.domain.Pageable;
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

    private final BookService service;

    @LogInvocation
    @GetMapping("/add")
    public String addToCart(HttpSession session, @RequestParam Long id, Pageable pageable) {
        Map<BookDto, Integer> cart = (Map) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }
        addBookToCart(id, cart);
        return "redirect:" + generatePath(pageable);
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

    private String generatePath(Pageable pageable) {
        int page = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        String sort = "asc";
        return "/books/all?page=" + page + "&size=" + pageSize + "&sort=" + sort;
    }

    private void addBookToCart(Long id, Map<BookDto, Integer> cart) {
        BookDto book = service.getById(id);
        if (cart.containsKey(book)) {
            cart.put(book, cart.get(book) + 1);
        } else {
            cart.put(book, 1);
        }
    }
}
