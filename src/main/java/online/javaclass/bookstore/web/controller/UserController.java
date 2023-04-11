package online.javaclass.bookstore.web.controller;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.OrderService;
import online.javaclass.bookstore.service.UserService;
import online.javaclass.bookstore.service.dto.OrderDto;
import online.javaclass.bookstore.service.dto.UserDto;
import online.javaclass.bookstore.service.dto.UserLoginDto;
import online.javaclass.bookstore.service.exceptions.AppException;
import online.javaclass.bookstore.web.filter.SecurityCheck;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final OrderService orderService;

    @LogInvocation
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityCheck(allowed = {UserDto.Role.ADMIN})
    public String add(@ModelAttribute UserDto userInModel, HttpSession session, Model model) {
        UserDto userInSession = (UserDto) session.getAttribute("user");
        if (userInSession == null || userInSession.getRole() != UserDto.Role.ADMIN) {
            userInModel.setRole(UserDto.Role.USER);
            userInModel.setRating(BigDecimal.ZERO);
        }
        UserDto created = userService.save(userInModel);
        model.addAttribute("user", created);
        return "user";
    }

    @LogInvocation
    @GetMapping("/add")
    @SecurityCheck(allowed = {UserDto.Role.ADMIN})
    public String addForm() {
        return "add_user";
    }


    @LogInvocation
    @PostMapping("/edit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @SecurityCheck(allowed = {UserDto.Role.ADMIN})
    public String edit(@ModelAttribute UserDto user, Model model) {
        UserDto edited = userService.save(user);
        model.addAttribute("user", edited);
        return "user";
    }

    @LogInvocation
    @GetMapping("/edit/{id}")
    @SecurityCheck(allowed = {UserDto.Role.ADMIN})
    public String editForm(@PathVariable Long id, Model model) {
        UserDto user = userService.getById(id);
        model.addAttribute("user", user);
        return "edit_user";
    }

    @LogInvocation
    @PostMapping("/login")
    public String login(HttpSession session, @ModelAttribute UserLoginDto user) {
        UserDto loggedIn = userService.login(user);
        session.setMaxInactiveInterval(86400);
        session.setAttribute("user", loggedIn);
        return "redirect:/home";
    }

    @LogInvocation
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @LogInvocation
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/home";
    }

    @LogInvocation
    @PostMapping("/{id}")
    @SecurityCheck(allowed = {UserDto.Role.ADMIN})
    public String getOne(@PathVariable Long id, Model model) {
        UserDto user = userService.getById(id);
        model.addAttribute("user", user);
        return "user";
    }

    @LogInvocation
    @GetMapping("my_orders")
    @SecurityCheck(allowed = {UserDto.Role.USER, UserDto.Role.ADMIN})
    public String getOrdersByUserId(Pageable pageable, @SessionAttribute UserDto user, Model model) {
        try {
            Page<OrderDto> page = orderService.getAllByUserId(pageable, user.getId());
            model.addAttribute("page", page.getNumber());
            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("orders", page.stream().toList());
            return "my_orders";
        } catch (AppException e) {
            return "error";
        }
    }

    @LogInvocation
    @GetMapping("/all")
    @SecurityCheck(allowed = {UserDto.Role.ADMIN})
    public String getAll(Pageable pageable, Model model) {
        Page<UserDto> page = userService.getAll(pageable);
        model.addAttribute("page", page.getNumber());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("users", page.stream().toList());
        return "users";
    }
}
