package online.javaclass.bookstore.controller.command.impl.orderCommands;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.FrontController;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.controller.utils.OrderControllerUtils;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

@Controller("corr_cart")
@RequiredArgsConstructor
public class CorrectCartCommand implements Command {

    private final OrderControllerUtils orderControllerUtils;

    @LogInvocation
    @Override
    public String execute(HttpServletRequest req) {
        String attributeName = "cart";
        orderControllerUtils.correctItemQuantity(req, attributeName);
        return FrontController.REDIRECT + "controller?command=cart";
    }
}