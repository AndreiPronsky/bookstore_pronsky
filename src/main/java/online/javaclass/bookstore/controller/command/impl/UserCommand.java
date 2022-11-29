package online.javaclass.bookstore.controller.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.UserService;
import online.javaclass.bookstore.service.dto.UserDto;

@Log4j2
public class UserCommand implements Command {
    private final UserService userService;

    public UserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        try {
            Long id = processId(req);
            UserDto user = userService.getById(id);
            req.setAttribute("user", user);
            return "jsp/user.jsp";
        } catch (Exception e) {
            log.error(e.getClass() + " " + e.getMessage());
            return "jsp/error.jsp";
        }
    }

    private Long processId(HttpServletRequest req) {
        try {
            String rawId = req.getParameter("id");
            return Long.parseLong(rawId);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(e.getMessage());
        }
    }
}
