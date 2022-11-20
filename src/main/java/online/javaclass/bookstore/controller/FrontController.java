package online.javaclass.bookstore.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.controller.command.CommandFactory;
import online.javaclass.bookstore.service.exceptions.AppException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet("/controller")
public class FrontController extends HttpServlet {
    private static final Logger log = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commandParameter = req.getParameter("command");
        Command command = CommandFactory.INSTANCE.getCommand(commandParameter);
        String page;
        try {
            page = command.execute(req);
        } catch (AppException e) {
            page = processError(req, e);
        }
        req.getRequestDispatcher(page).forward(req, resp);
    }

    private String processError(HttpServletRequest req, AppException e) {
        String page;
        page = CommandFactory.INSTANCE.getCommand("error").execute(req);
        String message = e.getMessage();
        req.setAttribute("message", message);
        return page;
    }

    @Override
    public void init() throws ServletException {
        log.info("FRONT CONTROLLER INITIALISED");
    }

    @Override
    public void destroy() {
        log.info("FRONT CONTROLLER DESTROYED");
    }
}
