package online.javaclass.bookstore.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.controller.command.CommandFactory;
import online.javaclass.bookstore.data.connection.DataBaseManager;
import online.javaclass.bookstore.service.exceptions.AppException;

import java.io.IOException;

@Log4j2
@WebServlet("/controller")
public class FrontController extends HttpServlet {

    public static final String REDIRECT = "REDIRECT:";
    private static final String DEFAULT_CLIENT_MESSAGE = "Ooops... Something went wrong";
    private static final String DEFAULT_SERVER_MESSAGE = "We are already working on the problem, we will soon solve this issue!";
    @Override
    public void init() {
        log.info("FRONT CONTROLLER INITIALISED");
    }

    @Override
    public void destroy() {
        DataBaseManager.INSTANCE.close();
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
        String commandParameter = req.getParameter("command");
        Command command = CommandFactory.INSTANCE.getCommand(commandParameter);
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
        return CommandFactory.INSTANCE.getCommand("error").execute(req);
    }

    private void verifyErrorAndSetMessage(HttpServletRequest req, HttpServletResponse resp, Exception e) {
        String message;
        if (e instanceof AppException) {
            message = e.getMessage();
            req.setAttribute("message", message);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else if (e instanceof NumberFormatException) {
            message = DEFAULT_CLIENT_MESSAGE;
            req.setAttribute("message", message);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        else {
            message = DEFAULT_SERVER_MESSAGE;
            req.setAttribute("message", message);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }


}
