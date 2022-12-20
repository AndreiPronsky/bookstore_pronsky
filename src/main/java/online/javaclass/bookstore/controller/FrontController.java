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

import java.io.IOException;

@Log4j2
@WebServlet("/controller")
public class FrontController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        HttpSession session = req.getSession();
//        ThreadLocal<String> threadLocal = new ThreadLocal<>();
//        threadLocal.set((String)session.getAttribute("lang"));
        String commandParameter = req.getParameter("command");
        Command command = CommandFactory.INSTANCE.getCommand(commandParameter);
        log.debug("Got command from factory " + commandParameter);
        String page;
        try {
            page = command.execute(req);
            log.debug("Command executed");
        } catch (Exception e) {
            log.error(e);
            page = processError(req, e);
        }
        req.getRequestDispatcher(page).forward(req, resp);
        log.debug("Forwarded");
    }

    private String processError(HttpServletRequest req, Exception e) {
        String page;
        page = CommandFactory.INSTANCE.getCommand("error").execute(req);
        String message = e.getMessage();
        req.setAttribute("message", message);
        return page;
    }

    @Override
    public void init() {
        log.info("FRONT CONTROLLER INITIALISED");
    }

    @Override
    public void destroy() {
        DataBaseManager.INSTANCE.close();
        log.info("FRONT CONTROLLER DESTROYED");
    }
}
