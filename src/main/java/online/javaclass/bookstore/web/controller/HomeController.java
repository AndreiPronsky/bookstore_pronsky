package online.javaclass.bookstore.web.controller;

import online.javaclass.bookstore.platform.MessageManager;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

@Controller
public class HomeController {

    @LogInvocation
    @GetMapping({"/", "/home"})
    public String execute(HttpSession session, HttpServletRequest request) {
        String lang = request.getLocale().getLanguage();
        if (session.getAttribute("lang") != null) {
            lang = (String) session.getAttribute("lang");
        }
        MessageManager.setLocale(lang);
        return "index";
    }
}