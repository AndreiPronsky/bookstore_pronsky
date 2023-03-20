package online.javaclass.bookstore.controller.command.impl.userCommands;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.controller.command.Command;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller("add_user_form")
public class AddUserFormCommand implements Command {

    @LogInvocation
    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/add_user.jsp";
    }
}
