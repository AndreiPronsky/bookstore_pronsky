package online.javaclass.bookstore.controller.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.UserService;
import online.javaclass.bookstore.service.dto.UserDto;

@Log4j2
@RequiredArgsConstructor
public class EditUserFormCommand implements Command {
    private final UserService userService;
    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        UserDto user = userService.getById(id);
        req.setAttribute("user", user);
        return "jsp/edit_user.jsp";
    }
}
