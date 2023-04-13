package online.javaclass.bookstore.web.controller;

import online.javaclass.bookstore.platform.logging.LogInvocation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpSession;

@Controller
public class ChangeLangController {

    @LogInvocation
    @RequestMapping("/change_lang")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String execute(HttpSession session, @RequestParam String lang) {
        session.setAttribute("lang", lang);
        return "index";
    }
}