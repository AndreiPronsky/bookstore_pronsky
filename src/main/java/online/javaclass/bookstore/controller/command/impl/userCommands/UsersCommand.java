package online.javaclass.bookstore.controller.command.impl.userCommands;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.LogInvocation;
import online.javaclass.bookstore.controller.PagingUtil;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.UserService;
import online.javaclass.bookstore.service.dto.PageableDto;
import online.javaclass.bookstore.service.dto.UserDto;
import org.springframework.stereotype.Controller;

import java.util.List;

@RequiredArgsConstructor
@Controller("users")
public class UsersCommand implements Command {
    private final UserService userService;

    @LogInvocation
    @Override
    public String execute(HttpServletRequest req) {
        PageableDto pageable = PagingUtil.getPageable(req);
        List<UserDto> users;
        users = userService.getAll(pageable);
        req.setAttribute("page", pageable.getPage());
        req.setAttribute("total_pages", pageable.getTotalPages());
        req.setAttribute("users", users);
        return "jsp/users.jsp";
    }
}
