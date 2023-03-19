package online.javaclass.bookstore.controller.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import online.javaclass.bookstore.LogInvocation;
import online.javaclass.bookstore.controller.command.Command;
import org.springframework.stereotype.Controller;

@Controller("change_lang")
public class ChangeLanguageCommand implements Command {

    @LogInvocation
    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String lang = req.getParameter("lang");
        session.setAttribute("lang", lang);
        return "index.jsp";
    }
}
