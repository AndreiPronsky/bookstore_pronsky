package online.javaclass.bookstore.controller.command.impl;

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
    @Override
    public String execute(HttpServletRequest req) {
        UserDto user = new UserDto();
        user.setId(Long.parseLong(req.getParameter("id")));
        user.setFirstName(req.getParameter("firstname"));
        user.setLastName(req.getParameter("lastname"));
        user.setEmail(req.getParameter("email"));
        user.setPassword(req.getParameter("password"));
        user.setRole(UserDto.Role.values()[Integer.parseInt(req.getParameter("role"))-1]);
        user.setRating(BigDecimal.valueOf(Double.parseDouble(req.getParameter("rating"))));
        UserDto newUser = userService.update(user);
        req.setAttribute("user", newUser);
        return "jsp/user.jsp";
    }
}
