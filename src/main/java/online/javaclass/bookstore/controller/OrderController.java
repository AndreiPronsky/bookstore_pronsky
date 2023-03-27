package online.javaclass.bookstore.controller;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.utils.PagingUtil;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.OrderService;
import online.javaclass.bookstore.service.dto.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/confirm_order")
    public String confirmOrder(@SessionAttribute Map<BookDto, Integer> cart,
                               @ModelAttribute OrderDto order, Model model) {
        order.setItems(listItems(cart));
        OrderDto created = orderService.create(order);
        model.addAttribute("order", created);
        return "successful_order";
    }

    @LogInvocation
    @PostMapping("/edit")
    public String editOrder(@ModelAttribute OrderDto order, Model model) {
        if (order.getOrderStatus() == null && order.getPaymentStatus() == null) {
            order.setOrderStatus(OrderDto.OrderStatus.OPEN);
            order.setPaymentStatus(OrderDto.PaymentStatus.UNPAID);
        }
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
    public String getAll(Model model) {
        PageableDto pageable = pagingUtil.getPageable(model);
        List<OrderDto> orders = orderService.getAll(pageable);
        model.addAttribute("page", pageable.getPage());
        model.addAttribute("total_pages", pageable.getTotalPages());
        model.addAttribute("orders", orders);
        return "orders";
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
}
