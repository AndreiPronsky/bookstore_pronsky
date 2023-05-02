package online.javaclass.bookstore.web.controller.view;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.OrderService;
import online.javaclass.bookstore.service.dto.BookDto;
import online.javaclass.bookstore.service.dto.OrderDto;
import online.javaclass.bookstore.service.dto.OrderItemDto;
import online.javaclass.bookstore.service.dto.UserDto;
import online.javaclass.bookstore.service.exceptions.ValidationException;
import online.javaclass.bookstore.web.filter.SecurityCheck;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
    public String confirmOrder(HttpSession session, @ModelAttribute OrderDto orderDto
            , BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "order/confirm_order";
        }
        moveItemsFromCartToOrder(session, orderDto);
        orderDto.setCost(calculateCost(orderDto.getItems()));
        UserDto user = (UserDto) session.getAttribute("user");
        orderDto.setUser(user);
        OrderDto created = orderService.save(orderDto);
        model.addAttribute("order", created);
        session.removeAttribute("cart");
        return "order/successful_order";
    }

    @LogInvocation
    @GetMapping("/confirm")
    public String confirmOrderForm(HttpSession session, Model model) {
        UserDto user = (UserDto) session.getAttribute("user");
        if (user == null) {
            return "redirect:/users/login";
        }
        OrderDto orderDto = new OrderDto();
        model.addAttribute("orderDto", orderDto);
        return "order/confirm_order";
    }

    @LogInvocation
    @PostMapping("/edit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @SecurityCheck(allowed = {UserDto.Role.USER, UserDto.Role.ADMIN})
    public String editOrder(@SessionAttribute @Valid OrderDto orderDto, Model model, @SessionAttribute UserDto user) {
        try {
            OrderDto updated = orderService.save(orderDto);
            model.addAttribute("orderDto", updated);
            if (user.getRole() == UserDto.Role.ADMIN) {
                return "order/order";
            }
            return "order/successful_order";
        } catch (ValidationException e) {
            return "order/edit_order";
        }
    }

    @LogInvocation
    @GetMapping("/edit/{id}")
    public String editOrderForm(@PathVariable Long id, HttpSession session) {
        OrderDto orderDto = orderService.getById(id);
        UserDto user = (UserDto) session.getAttribute("user");
        if (user == null || notMatchingUserIgnoreAdmin(user, orderDto)) {
            return "redirect:/index";
        } else if (notAdminRole(user) && notOpenStatus(orderDto)) {
            return "redirect:index";
        }
        session.setAttribute("orderDto", orderDto);
        return "order/edit_order";
    }

    @LogInvocation
    @GetMapping("/{id}")
    public String getOne(@PathVariable Long id, Model model) {
        OrderDto orderDto = orderService.getById(id);
        model.addAttribute("orderDto", orderDto);
        return "order/order";
    }

    @LogInvocation
    @GetMapping("/all")
    @SecurityCheck(allowed = {UserDto.Role.ADMIN})
    public String getAll(Pageable pageable, Model model) {
        Page<OrderDto> page = orderService.getAll(pageable);
        model.addAttribute("page", page.getNumber());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("orders", page.stream().toList());
        return "order/orders";
    }

    @LogInvocation
    @GetMapping("/all/{id}")
    @SecurityCheck(allowed = {UserDto.Role.USER, UserDto.Role.ADMIN})
    public String getAllByUserId(Pageable pageable, Model model, @PathVariable Long id) {
        Page<OrderDto> page = orderService.getAllByUserId(pageable, id);
        model.addAttribute("page", page.getNumber());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("orders", page.stream().toList());
        return "order/my_orders";
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

    private static void moveItemsFromCartToOrder(HttpSession session, OrderDto orderDto) {
        Map<BookDto, Integer> cart = (Map) session.getAttribute("cart");
        List<OrderItemDto> items = new ArrayList<>();
        for (Map.Entry<BookDto, Integer> entry : cart.entrySet()) {
            OrderItemDto item = new OrderItemDto();
            item.setBook(entry.getKey());
            item.setQuantity(entry.getValue());
            item.setPrice(entry.getKey().getPrice());
            items.add(item);
        }
        orderDto.setItems(items);
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
        OrderDto orderDto = (OrderDto) session.getAttribute("orderDto");
        List<OrderItemDto> items = orderDto.getItems();
        OrderItemDto itemToEdit = items.get(0);
        for (OrderItemDto item : items) {
            if (item.getId().equals(id)) {
                itemToEdit = item;
            }
        }
        Integer quantity = itemToEdit.getQuantity();
        if (action.equals("dec") && quantity > 1) {
            itemToEdit.setQuantity(quantity - 1);
        }
        if (action.equals("inc")) {
            itemToEdit.setQuantity(quantity + 1);
        }
        if (action.equals("remove")) {
            items.remove(itemToEdit);
        }
        orderDto.setItems(items);
        orderDto.setCost(calculateCost(items));
        orderService.save(orderDto);
        session.setAttribute("orderDto", orderDto);
        return "redirect:/orders/edit/" + orderDto.getId();
    }
}

