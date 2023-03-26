package online.javaclass.bookstore.controller.command.impl.userCommands;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller("add_user_form")
public class AddUserFormCommand implements Command {

    @LogInvocation
    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/add_user.jsp";
    }
}
