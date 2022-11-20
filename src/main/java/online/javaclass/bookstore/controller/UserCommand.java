package online.javaclass.bookstore.controller;

import jakarta.servlet.http.HttpServletRequest;
import online.javaclass.bookstore.service.UserService;
import online.javaclass.bookstore.service.dto.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private final UserService userService;

    public UserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req) {
            Long id = processId(req);
            UserDto user = userService.getById(id);
            req.setAttribute("user", user);
            return "jsp/user.jsp";
    }

    private Long processId(HttpServletRequest req) {
        try {
            String rawId = req.getParameter("id");
            return Long.parseLong(rawId);
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }
}
