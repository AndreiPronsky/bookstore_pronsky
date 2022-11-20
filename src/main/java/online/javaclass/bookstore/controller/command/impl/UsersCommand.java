package online.javaclass.bookstore.controller.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.UserService;
import online.javaclass.bookstore.service.dto.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UsersCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private final UserService userService;

    public UsersCommand(UserService userService) {
        this.userService = userService;
    }
    @Override
    public String execute(HttpServletRequest req) {
        List<UserDto> users;
        String rawLastName = req.getParameter("lastname");
        if (rawLastName == null) {
            users = userService.getAll();
        } else {
            String lastName = reformatLastName(rawLastName);
            users = userService.getByLastName(lastName);
        }
        req.setAttribute("users", users);
        return "jsp/users.jsp";
    }

    private String reformatLastName(String rawLastName) {
        return rawLastName.replace("%20", " ");
    }
}
