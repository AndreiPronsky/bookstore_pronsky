package online.javaclass.bookstore.controller.command.impl.userCommands;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.utils.PagingUtil;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.UserService;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller("users")
public class UsersCommand implements Command {
    private final UserService userService;
    private final PagingUtil pagingUtil;

    @LogInvocation
    @Override
    public String execute(HttpServletRequest req) {
//        PageableDto pageable = pagingUtil.getPageable(req);
//        List<UserDto> users;
//        users = userService.getAll(pageable);
//        req.setAttribute("page", pageable.getPage());
//        req.setAttribute("total_pages", pageable.getTotalPages());
//        req.setAttribute("users", users);
        return "jsp/users.jsp";
    }
}
