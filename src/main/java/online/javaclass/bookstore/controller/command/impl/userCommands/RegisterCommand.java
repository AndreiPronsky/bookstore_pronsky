package online.javaclass.bookstore.controller.command.impl.userCommands;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.controller.utils.UserControllerUtils;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.UserService;
import online.javaclass.bookstore.service.dto.UserDto;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@RequiredArgsConstructor
@Controller("register")
public class RegisterCommand implements Command {

    private final UserService userService;
    private final UserDto.Role defaultRole = UserDto.Role.USER;
    private final BigDecimal defaultRating = BigDecimal.ZERO;
    private final UserControllerUtils userCommandUtils;

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