package online.javaclass.bookstore.controller;

import online.javaclass.bookstore.platform.logging.LogInvocation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/change_lang")
public class ChangeLangController {

    @LogInvocation
    public String execute(@RequestParam String lang, HttpSession session) {
        session.setAttribute("lang", lang);
        return "index.jsp";
    }
}
