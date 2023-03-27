package online.javaclass.bookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/error")
public class ErrorController {
    public String execute(HttpServletRequest req) {
        return "jsp/error.jsp";
    }
}
