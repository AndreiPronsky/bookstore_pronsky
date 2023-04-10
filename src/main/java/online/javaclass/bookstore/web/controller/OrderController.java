package online.javaclass.bookstore.web.controller;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.OrderService;
import online.javaclass.bookstore.service.dto.BookDto;
import online.javaclass.bookstore.service.dto.OrderDto;
import online.javaclass.bookstore.service.dto.OrderItemDto;
import online.javaclass.bookstore.service.dto.UserDto;
import online.javaclass.bookstore.web.filter.SecurityCheck;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @LogInvocation
    @PostMapping("/confirm")
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityCheck(allowed = {UserDto.Role.USER})
    public String confirmOrder(HttpSession session, @ModelAttribute OrderDto order, Model model) {
        Map<BookDto, Integer> cart = (Map) session.getAttribute("cart");
        List<OrderItemDto> items = listItems(cart);
        order.setItems(items);
        BigDecimal cost = calculateCost(items);
        order.setCost(cost);
        UserDto user = (UserDto) session.getAttribute("user");
        if (user.getRole() == UserDto.Role.USER) {
            order.setUser(user);
        }
        OrderDto created = orderService.save(order);
        model.addAttribute("order", created);
        cart.clear();
        return "successful_order";
    }

    @LogInvocation
    @GetMapping("/confirm")
    public String confirmOrderForm(HttpSession session) {
        UserDto user = (UserDto) session.getAttribute("user");
        if (user == null) {
            return "redirect:/users/login";
        }
        return "confirm_order";
    }

    @LogInvocation
    @PostMapping("/edit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @SecurityCheck(allowed = {UserDto.Role.USER, UserDto.Role.ADMIN})
    public String editOrder(@SessionAttribute OrderDto order, Model model, @SessionAttribute UserDto user) {
        OrderDto updated = orderService.save(order);
        model.addAttribute("order", updated);
        if (user.getRole() == UserDto.Role.ADMIN) {
            return "order";
        }
        return "successful_order";
    }

    @LogInvocation
    @GetMapping("/edit/{id}")
    public String editOrderForm(@PathVariable Long id, HttpSession session, @SessionAttribute UserDto user) {
        OrderDto order = orderService.getById(id);
        if (user == null || notMatchingUserIgnoreAdmin(user, order)) {
            return "redirect:index";
        } else if (notAdminRole(user) && notOpenStatus(order)) {
            return "redirect:index";
        }
        session.setAttribute("order", order);
        return "edit_order";
    }

    @LogInvocation
    @GetMapping("/{id}")
    public String getOne(@PathVariable Long id, Model model) {
        OrderDto order = orderService.getById(id);
        model.addAttribute("order", order);
        return "order";
    }

    @LogInvocation
    @GetMapping("/all")
    @SecurityCheck(allowed = {UserDto.Role.ADMIN})
    public String getAll(Pageable pageable, Model model) {
        Page<OrderDto> page = orderService.getAll(pageable);
        model.addAttribute("page", page.getNumber());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("orders", page.stream().toList());
        return "orders";
    }

    @LogInvocation
    @GetMapping("/all/{id}")
    @SecurityCheck(allowed = {UserDto.Role.USER, UserDto.Role.ADMIN})
    public String getAllByUserId(Model model, @PathVariable Long id) {
        List<OrderDto> orders = orderService.getOrdersByUserId(id);
        model.addAttribute("orders", orders);
        return "my_orders";
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

    private List<OrderItemDto> listItems(Map<BookDto, Integer> itemMap) {
        List<OrderItemDto> items = new ArrayList<>();
        for (Map.Entry<BookDto, Integer> entry : itemMap.entrySet()) {
            OrderItemDto itemDto = new OrderItemDto();
            itemDto.setQuantity(entry.getValue());
            itemDto.setBook(entry.getKey());
            itemDto.setPrice(entry.getKey().getPrice());
            items.add(itemDto);
        }
        return items;
    }

    private BigDecimal calculateCost(List<OrderItemDto> items) {
        BigDecimal cost = BigDecimal.ZERO;
        for (OrderItemDto item : items) {
            cost = cost.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return cost;
    }

    @LogInvocation
    @RequestMapping("/edit/edit_IQ")
    public String correctItemQuantity(HttpSession session, @RequestParam String action, @RequestParam Long id) {
        OrderDto order = (OrderDto) session.getAttribute("order");
        List<OrderItemDto> items = order.getItems();
        for (OrderItemDto item : items) {
            if (item.getId().equals(id)) {
                Integer quantity = item.getQuantity();
                if (action.equals("dec") && quantity > 1) {
                    item.setQuantity(quantity - 1);
                }
                if (action.equals("inc")) {
                    item.setQuantity(quantity + 1);
                }
                if (action.equals("remove")) {
                    items.remove(item);
                }
            }
        }
        order.setCost(calculateCost(items));
        orderService.save(order);
        return "redirect:/orders/edit/" + order.getId();
    }
}

