package online.javaclass.bookstore.controller.command.impl.userCommands;

import jakarta.servlet.http.HttpServletRequest;
import online.javaclass.bookstore.service.dto.UserDto;

import java.math.BigDecimal;

public class UserCommandUtils {
    static UserDto setUserParameters(HttpServletRequest req, UserDto.Role role, BigDecimal rating) {
        UserDto user = new UserDto();
        user.setFirstName(req.getParameter("firstname").toLowerCase());
        user.setLastName(req.getParameter("lastname"));
        user.setEmail(req.getParameter("email"));
        user.setPassword(req.getParameter("password"));
        user.setRole(role);
        user.setRating(rating);
        return user;
    }
}
