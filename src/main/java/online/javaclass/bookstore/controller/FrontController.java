package online.javaclass.bookstore.controller;

import online.javaclass.bookstore.AppContextListener;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.exceptions.AppException;
import online.javaclass.bookstore.exceptions.ValidationException;
import online.javaclass.bookstore.platform.MessageManager;
import online.javaclass.bookstore.platform.logging.LogInvocation;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

//@WebServlet("/controller")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class FrontController extends HttpServlet {

    public static final String REDIRECT = "REDIRECT:";
    private final MessageManager messageManager = AppContextListener.getContext()
            .getBean("messageManager", MessageManager.class);

    @LogInvocation
    @Override
    public void init() {
    }

    @LogInvocation
    @Override
    public void destroy() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @LogInvocation
    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String lang = req.getLocale().getLanguage();
        if (session.getAttribute("lang") != null) {
            lang = (String) session.getAttribute("lang");
        }
        MessageManager.setLocale(lang);
        String commandParameter = req.getParameter("command");
        Command command = AppContextListener.getContext().getBean(commandParameter, Command.class);
        String page;
        try {
            page = command.execute(req);
        } catch (Exception e) {
            page = processError(req, resp, e);
        }
        processResponse(req, resp, page);
    }

    private static void processResponse(HttpServletRequest req, HttpServletResponse resp, String page) throws IOException, ServletException {
        if (page.startsWith(REDIRECT)) {
            resp.sendRedirect(page.substring(REDIRECT.length()));
        } else {
            req.getRequestDispatcher(page).forward(req, resp);
        }
    }

    private String processError(HttpServletRequest req, HttpServletResponse resp, Exception e) {
        verifyErrorAndSetMessage(req, resp, e);
        return AppContextListener.getContext().getBean("error", Command.class).execute(req);
    }

    private void verifyErrorAndSetMessage(HttpServletRequest req, HttpServletResponse resp, Exception e) {
        String message;
        if (e instanceof ValidationException) {
            List<String> messages = ((ValidationException) e).getMessages();
            req.setAttribute("messages", messages);
        } else if (e instanceof AppException) {
            message = e.getMessage();
            req.setAttribute("message", message);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else if (e instanceof NumberFormatException) {
            message = messageManager.getMessage("error.default_client");
            req.setAttribute("message", message);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            message = messageManager.getMessage("error.default_server");
            req.setAttribute("message", message);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
