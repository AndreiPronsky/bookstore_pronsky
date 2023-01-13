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
public class AddUserCommand implements Command {

    private final UserService userService;
    private static UserCommandUtils userCommandUtils = new UserCommandUtils();

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
