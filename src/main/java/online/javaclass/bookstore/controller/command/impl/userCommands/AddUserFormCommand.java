package online.javaclass.bookstore.controller.command.impl.userCommands;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.controller.command.Command;
import org.springframework.stereotype.Controller;

@Log4j2
@RequiredArgsConstructor
@Controller("add_user_form")
public class AddUserFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/add_user.jsp";
    }
}
