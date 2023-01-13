package online.javaclass.bookstore.controller.command.impl.userCommands;

import jakarta.servlet.http.HttpServletRequest;
import online.javaclass.bookstore.MessageManager;
import online.javaclass.bookstore.exceptions.ValidationException;
import online.javaclass.bookstore.service.dto.UserDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UserCommandUtils {

    public static final String VALID_EMAIL_REGEX = "^[\\w\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    MessageManager messageManager = MessageManager.INSTANCE;
    UserDto setUserParameters(HttpServletRequest req, UserDto.Role role, BigDecimal rating) throws ValidationException {
        validate(req);
        UserDto user = new UserDto();
        user.setFirstName(req.getParameter("firstname"));
        user.setLastName(req.getParameter("lastname"));
        user.setEmail(req.getParameter("email").toLowerCase());
        user.setPassword(req.getParameter("password"));
        user.setRole(role);
        user.setRating(rating);
        return user;
    }

    private void validate(HttpServletRequest req) throws ValidationException {
        List<String> messages = new ArrayList<>();
        if (req.getParameter("firstname") == null || req.getParameter("lastname").isBlank()) {
            messages.add(messageManager.getMessage("error.invalid_firstname"));
        }
        if (req.getParameter("lastname") == null || req.getParameter("lastname").isBlank()) {
            messages.add(messageManager.getMessage("error.invalid_lastname"));
        }
        if (req.getParameter("email") == null || !req.getParameter("email").matches(VALID_EMAIL_REGEX)) {
            messages.add(messageManager.getMessage("error.invalid_email"));
        }
        if (req.getParameter("password") == null || (req.getParameter("password").length() < 8)) {
            messages.add(messageManager.getMessage("error.invalid_password"));
        }
        if (BigDecimal.valueOf(Double.parseDouble(req.getParameter("rating")))
                .compareTo(BigDecimal.ZERO) < 0 ||
                BigDecimal.valueOf(Double.parseDouble(req.getParameter("rating")))
                        .compareTo(BigDecimal.valueOf(5)) > 0) {
            messages.add(messageManager.getMessage("error.invalid rating"));
        }
        if (!messages.isEmpty()) {
            throw new ValidationException(messages);
        }
    }
}
