package online.javaclass.bookstore.controller.command.impl.userCommands;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.UserService;
import online.javaclass.bookstore.service.dto.UserDto;

import java.math.BigDecimal;

import static online.javaclass.bookstore.controller.command.impl.userCommands.UserCommandUtils.setUserParameters;

@Log4j2
@RequiredArgsConstructor
public class RegisterCommand implements Command {

    private final UserService userService;
    private final UserDto.Role defaultRole = UserDto.Role.USER;
    private final BigDecimal defaultRating = BigDecimal.ZERO;
    @Override
    public String execute(HttpServletRequest req) {
        UserDto user = setUserParameters(req, defaultRole, defaultRating);
        UserDto newUser = userService.create(user);
        HttpSession session = req.getSession();
        session.setAttribute("user", newUser);
        req.setAttribute("user", newUser);
        return "jsp/user.jsp";
    }
}