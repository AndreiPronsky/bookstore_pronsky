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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final OrderService orderService;
    private final LocaleResolver localeResolver;

    @LogInvocation
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityCheck(allowed = {UserDto.Role.ADMIN, UserDto.Role.NONE})
    public String add(@ModelAttribute @Valid UserDto userInModel, BindingResult result, HttpSession session, Model model) {
        if (result.hasErrors()) {
            return "add_user";
        }
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
    @SecurityCheck(allowed = {UserDto.Role.ADMIN, UserDto.Role.NONE})
    public String addForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "add_user";
    }


    @LogInvocation
    @PostMapping("/edit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @SecurityCheck(allowed = {UserDto.Role.ADMIN})
    public String edit(@ModelAttribute @Valid UserDto user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "edit_user";
        }
        UserDto edited = userService.save(user);
        model.addAttribute("user", edited);
        return "user";
    }

    @LogInvocation
    @GetMapping("/edit/{id}")
    @SecurityCheck(allowed = {UserDto.Role.ADMIN})
    public String editForm(@PathVariable Long id, Model model) {
        UserDto user = userService.getById(id);
        model.addAttribute("userDto", user);
        return "edit_user";
    }

    @LogInvocation
    @PostMapping("/login")
    @SecurityCheck(allowed = {UserDto.Role.NONE})
    public String login(HttpServletRequest req, HttpServletResponse res,
                        @ModelAttribute @Valid UserLoginDto user) {
        UserDto loggedIn = userService.login(user);
        String lang = loggedIn.getPreferredLocale();
        HttpSession session = req.getSession();
        session.setAttribute("lang", lang);
        session.setMaxInactiveInterval(86400);
        session.setAttribute("user", loggedIn);
        localeResolver.setLocale(req, res, new Locale(lang));
        return "redirect:/home";
    }

    @LogInvocation
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginDto", new UserLoginDto());
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
        model.addAttribute("userDto", user);
        return "user";
    }

    @LogInvocation
    @GetMapping("my_orders")
    @SecurityCheck(allowed = {UserDto.Role.USER, UserDto.Role.ADMIN})
    public String getOrdersByUserId(Pageable pageable, @SessionAttribute @Valid UserDto user, Model model) {
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
