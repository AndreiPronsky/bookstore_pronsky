package online.javaclass.bookstore.controller.command.impl.userCommands;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.UserService;
import online.javaclass.bookstore.service.dto.UserDto;

import java.math.BigDecimal;

@Log4j2
@RequiredArgsConstructor
public class EditUserCommand implements Command {
    private final UserService userService;
    private static UserCommandUtils userCommandUtils = new UserCommandUtils();

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
