package online.javaclass.bookstore.web.controller.view;

import online.javaclass.bookstore.platform.logging.LogInvocation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @LogInvocation
    @GetMapping({"/", "/home"})
    public String execute() {
        return "index";
    }
}
