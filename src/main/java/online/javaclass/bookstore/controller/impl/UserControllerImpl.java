package online.javaclass.bookstore.controller.impl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import online.javaclass.bookstore.data.connection.DataBaseManager;
import online.javaclass.bookstore.data.dao.impl.UserDaoImpl;
import online.javaclass.bookstore.service.UserService;
import online.javaclass.bookstore.service.dto.UserDto;
import online.javaclass.bookstore.service.impl.UserServiceImpl;

import java.io.IOException;

@WebServlet("/user")
public class UserControllerImpl extends HttpServlet {
    private final DataBaseManager manager = new DataBaseManager();
    private final UserService userService = new UserServiceImpl(new UserDaoImpl(manager));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
            Long id = processId(req);
            UserDto user = userService.getById(id);
            req.setAttribute("user", user);
            req.getRequestDispatcher("jsp/user.jsp").forward(req, resp);
    }

    private Long processId(HttpServletRequest req) {
        try {
            String rawId = req.getParameter("id");
            return Long.parseLong(rawId);
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }
}
