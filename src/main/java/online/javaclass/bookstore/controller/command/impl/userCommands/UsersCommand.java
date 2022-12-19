package online.javaclass.bookstore.controller.command.impl.userCommands;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.controller.PagingUtil;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.UserService;
import online.javaclass.bookstore.service.dto.PageableDto;
import online.javaclass.bookstore.service.dto.UserDto;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class UsersCommand implements Command {
    private final UserService userService;

    @Override
    public String execute(HttpServletRequest req) {
        List<UserDto> users;
        try {
            PageableDto pageable = PagingUtil.getPageable(req);
            users = userService.getAll(pageable);
            req.setAttribute("page", pageable.getPage());
            req.setAttribute("total_pages", pageable.getTotalPages());
            req.setAttribute("users", users);
            return "jsp/users.jsp";
        } catch (Exception e) {
            log.error(e.getClass() + " " + e.getMessage());
            return "jsp/error.jsp";
        }
    }
}
