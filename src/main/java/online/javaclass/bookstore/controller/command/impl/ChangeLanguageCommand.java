package online.javaclass.bookstore.controller.command.impl;

import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
