package online.javaclass.bookstore.controller;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.utils.PagingUtil;
import online.javaclass.bookstore.exceptions.AppException;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.OrderService;
import online.javaclass.bookstore.service.UserService;
import online.javaclass.bookstore.service.dto.OrderDto;
import online.javaclass.bookstore.service.dto.PageableDto;
import online.javaclass.bookstore.service.dto.UserDto;
import online.javaclass.bookstore.service.dto.UserLoginDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final OrderService orderService;
    private final PagingUtil pagingUtil;

    @LogInvocation
    @PostMapping("/add")
    public String add(@ModelAttribute UserDto userInModel,
                      @SessionAttribute UserDto userInSession,
                      Model model) {
        if (userInSession.getRole() != UserDto.Role.ADMIN) {
            userInModel.setRole(UserDto.Role.USER);
            userInModel.setRating(BigDecimal.ZERO);
        }
        UserDto created = userService.create(userInModel);
        model.addAttribute("user", created);
        return "/user";
    }

    @LogInvocation
    @GetMapping("/add")
    public String addForm() {
        return "add_user";
    }


    @LogInvocation
    @PostMapping("/edit/{id}")
    public String edit(@ModelAttribute UserDto user, Model model) {
        UserDto edited = userService.update(user);
        model.addAttribute("user", edited);
        return "user";
    }

    @LogInvocation
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        UserDto user = userService.getById(id);
        model.addAttribute("user", user);
        return "edit_user";
    }

    @LogInvocation
    @PostMapping("/login")
    public String login(@RequestAttribute HttpSession session, @ModelAttribute UserLoginDto user) {
        UserDto loggedIn = userService.login(user);
        session.setMaxInactiveInterval(86400);
        session.setAttribute("user", loggedIn);
        return "index";
    }

    @LogInvocation
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @LogInvocation
    @PostMapping("/logout")
    public String logout(@RequestParam HttpSession session) {
        session.invalidate();
        return "index";
    }

    @LogInvocation
    @PostMapping("/{id}")
    public String getOne(@PathVariable Long id, Model model) {
        UserDto user = userService.getById(id);
        model.addAttribute("user", user);
        return "user";
    }

    @LogInvocation
    @GetMapping("my_orders")
    public String getOrdersByUserId(@SessionAttribute UserDto user, Model model) {
        try {
            List<OrderDto> orders = orderService.getOrdersByUserId(user.getId());
            model.addAttribute("orders", orders);
            return "my_orders";
        } catch (AppException e) {
            return "error";
        }
    }

    @LogInvocation
    @GetMapping("/all")
    public String getAll(Model model) {
        PageableDto pageable = pagingUtil.getPageable(model);
        List<UserDto> users = userService.getAll(pageable);
        model.addAttribute("page", pageable.getPage());
        model.addAttribute("total_pages", pageable.getTotalPages());
        model.addAttribute("users", users);
        return "users";
    }
}
