package online.javaclass.bookstore.controller.command.impl.userCommands;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.LogInvocation;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.UserService;
import online.javaclass.bookstore.service.dto.UserDto;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Controller("register")
public class RegisterCommand implements Command {

    private final UserService userService;
    private final UserDto.Role defaultRole = UserDto.Role.USER;
    private final BigDecimal defaultRating = BigDecimal.ZERO;
    private final UserCommandUtils userCommandUtils;

    @LogInvocation
    @Override
    public String execute(HttpServletRequest req) {
        UserDto user = userCommandUtils.setUserParameters(req, defaultRole, defaultRating);
        UserDto newUser = userService.create(user);
        HttpSession session = req.getSession();
        session.setAttribute("user", newUser);
        req.setAttribute("user", newUser);
        return "jsp/user.jsp";
    }
}