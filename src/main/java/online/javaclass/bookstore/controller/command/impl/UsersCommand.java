package online.javaclass.bookstore.controller.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.UserService;
import online.javaclass.bookstore.service.dto.UserDto;

import java.util.List;

@Log4j2
public class UsersCommand implements Command {
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
