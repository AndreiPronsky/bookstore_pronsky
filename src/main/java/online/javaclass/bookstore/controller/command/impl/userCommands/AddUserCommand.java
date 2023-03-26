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
@Controller("add_user")
public class AddUserCommand implements Command {

    private final UserService userService;
    private final UserControllerUtils userCommandUtils;

    @LogInvocation
    @Override
    public String execute(HttpServletRequest req) {
        UserDto.Role role;
        BigDecimal rating;
        HttpSession session = req.getSession();
        if (session.getAttribute("user") == null) {
            role = UserDto.Role.USER;
            rating = BigDecimal.ZERO;
        } else {
            role = UserDto.Role.valueOf(req.getParameter("role"));
            rating = BigDecimal.valueOf(Double.parseDouble(req.getParameter("rating")));
        }
        UserDto user = userCommandUtils.setUserParameters(req, role, rating);
        UserDto newUser = userService.create(user);
        req.setAttribute("user", newUser);
        return "jsp/user.jsp";
    }
}
