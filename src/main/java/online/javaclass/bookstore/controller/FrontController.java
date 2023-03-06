package online.javaclass.bookstore.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.AppContextListener;
import online.javaclass.bookstore.MessageManager;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.exceptions.AppException;
import online.javaclass.bookstore.exceptions.ValidationException;

import java.io.IOException;
import java.util.List;

@Log4j2
@WebServlet("/controller")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class FrontController extends HttpServlet {

    public static final String REDIRECT = "REDIRECT:";
    private final MessageManager messageManager = AppContextListener.getContext().getBean("messageManager", MessageManager.class);


    @Override
    public void init() {
        log.info("FRONT CONTROLLER INITIALISED");
    }

    @Override
    public void destroy() {
        log.info("FRONT CONTROLLER DESTROYED");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String lang = req.getLocale().getLanguage();
        if (session.getAttribute("lang") != null) {
            lang = (String)session.getAttribute("lang");
        }
        MessageManager.setLocale(lang);
        String commandParameter = req.getParameter("command");
        Command command = AppContextListener.getContext().getBean(commandParameter, Command.class);
        log.debug("Got command from factory " + commandParameter);
        String page;
        try {
            page = command.execute(req);
            log.debug("Command executed");
        } catch (Exception e) {
            log.error(e.getMessage());
            page = processError(req, resp, e);
        }
        processResponse(req, resp, page);
    }

    private static void processResponse(HttpServletRequest req, HttpServletResponse resp, String page) throws IOException, ServletException {
        if (page.startsWith(REDIRECT)) {
            resp.sendRedirect(page.substring(REDIRECT.length()));
            log.debug("Redirected");
        } else {
            req.getRequestDispatcher(page).forward(req, resp);
            log.debug("Forwarded");
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
        }
        else if (e instanceof AppException) {
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
