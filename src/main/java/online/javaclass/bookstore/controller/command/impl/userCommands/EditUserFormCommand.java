package online.javaclass.bookstore.controller.command.impl.userCommands;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.UserService;
import online.javaclass.bookstore.service.dto.UserDto;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller("edit_user_form")
public class EditUserFormCommand implements Command {
    private final UserService userService;

    @LogInvocation
    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        UserDto user = userService.getById(id);
        req.setAttribute("user", user);
        return "jsp/edit_user.jsp";
    }
}
