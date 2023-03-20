package online.javaclass.bookstore.controller.command.impl.userCommands;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.UserService;
import online.javaclass.bookstore.service.dto.UserDto;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller("login")
public class LoginCommand implements Command {
    private final UserService userService;

    @LogInvocation
    @Override
    public String execute(HttpServletRequest req) {
        String email = req.getParameter("email").toLowerCase();
        String password = req.getParameter("password");
        UserDto userDto = userService.login(email, password);
        HttpSession session = req.getSession();
        session.setMaxInactiveInterval(86400);
        session.setAttribute("user", userDto);
        return "index.jsp";
    }
}
