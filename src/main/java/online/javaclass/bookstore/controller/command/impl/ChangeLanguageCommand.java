package online.javaclass.bookstore.controller.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import online.javaclass.bookstore.controller.command.Command;

import java.util.Locale;

public class ChangeLanguageCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String lang = req.getParameter("lang");
        Locale locale = new Locale(lang);
        session.setAttribute("lang", locale);
        return "index.jsp";
    }
}
