package online.javaclass.bookstore.controller.command.impl.userCommands;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.UserService;
import online.javaclass.bookstore.service.dto.UserDto;

import java.math.BigDecimal;

@Log4j2
@RequiredArgsConstructor
public class RegisterCommand implements Command {

    private final UserService userService;

    @Override
    public String execute(HttpServletRequest req) {
        UserDto user = new UserDto();
        user.setFirstName(req.getParameter("firstname"));
        user.setLastName(req.getParameter("lastname"));
        user.setEmail(req.getParameter("email"));
        user.setPassword(req.getParameter("password"));
        user.setRole(UserDto.Role.USER);
        user.setRating(BigDecimal.ZERO);
        UserDto newUser = userService.create(user);
        HttpSession session = req.getSession();
        session.setAttribute("user", newUser);
        req.setAttribute("user", newUser);
        return "jsp/user.jsp";
    }
}