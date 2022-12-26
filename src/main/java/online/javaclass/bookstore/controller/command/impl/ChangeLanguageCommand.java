package online.javaclass.bookstore.controller.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import online.javaclass.bookstore.controller.command.Command;

public class ChangeLanguageCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String lang = req.getParameter("lang");
        session.setAttribute("lang", lang);
        return "index.jsp";
    }
}
