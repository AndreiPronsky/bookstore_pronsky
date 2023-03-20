package online.javaclass.bookstore.controller.command.impl.userCommands;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.UserService;
import online.javaclass.bookstore.service.dto.UserDto;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Controller("edit_user")
public class EditUserCommand implements Command {
    private final UserService userService;
    private final UserCommandUtils userCommandUtils;

    @LogInvocation
    @Override
    public String execute(HttpServletRequest req) {
        BigDecimal rating = BigDecimal.valueOf(Double.parseDouble(req.getParameter("rating")));
        UserDto.Role role = UserDto.Role.valueOf(req.getParameter("role"));
        UserDto user = userCommandUtils.setUserParameters(req, role, rating);
        user.setId(Long.parseLong(req.getParameter("id")));
        UserDto newUser = userService.update(user);
        req.setAttribute("user", newUser);
        return "jsp/user.jsp";
    }
}
