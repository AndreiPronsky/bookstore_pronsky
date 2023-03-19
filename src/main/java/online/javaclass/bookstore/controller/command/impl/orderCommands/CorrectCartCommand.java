package online.javaclass.bookstore.controller.command.impl.orderCommands;

import jakarta.servlet.http.HttpServletRequest;
import online.javaclass.bookstore.LogInvocation;
import online.javaclass.bookstore.controller.FrontController;
import online.javaclass.bookstore.controller.command.Command;
import org.springframework.stereotype.Controller;

@Controller("corr_cart")
public class CorrectCartCommand implements Command {
    @LogInvocation
    @Override
    public String execute(HttpServletRequest req) {
        String attributeName = "cart";
        OrderCommandUtils.correctItemQuantity(req, attributeName);
        return FrontController.REDIRECT + "controller?command=cart";
    }
}