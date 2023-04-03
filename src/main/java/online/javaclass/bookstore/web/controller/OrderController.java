package online.javaclass.bookstore.web.controller;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.OrderService;
import online.javaclass.bookstore.service.dto.*;
import online.javaclass.bookstore.web.utils.PagingUtil;
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
    private final PagingUtil pagingUtil;

    @LogInvocation
    @PostMapping("/confirm")
    @ResponseStatus(HttpStatus.CREATED)
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
        OrderDto created = orderService.create(order);
        model.addAttribute("order", created);
        cart.clear();
        return "successful_order";
    }

    @LogInvocation
    @GetMapping("/confirm")
    public String confirmOrderForm(HttpSession session) {
        UserDto user = (UserDto) session.getAttribute("user");
        if (user == null) {
            return "redirect: /users/login";
        }
        return "confirm_order";
    }

    @LogInvocation
    @PostMapping("/edit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String editOrder(@ModelAttribute OrderDto order, Model model) {
        OrderDto updated = orderService.update(order);
        model.addAttribute("order", updated);
        return "successful_order";
    }

    @LogInvocation
    @GetMapping("/edit/{id}")
    public String editOrderForm(@PathVariable Long id, Model model, @SessionAttribute UserDto user) {
        OrderDto order = orderService.getById(id);
        if (user == null || notMatchingUserIgnoreAdmin(user, order)) {
            return "redirect: index";
        } else if (notAdminRole(user) && notOpenStatus(order)) {
            return "redirect: index";
        }
        model.addAttribute("order", order);
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
    public String getAll(@RequestParam String page, @RequestParam String page_size, Model model) {
        PageableDto pageable = pagingUtil.getPageable(page, page_size);
        List<OrderDto> orders = orderService.getAll(pageable);
        model.addAttribute("page", pageable.getPage());
        model.addAttribute("total_pages", pageable.getTotalPages());
        model.addAttribute("orders", orders);
        return "orders";
    }

    @LogInvocation
    @GetMapping("/all/${id}")
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
}
