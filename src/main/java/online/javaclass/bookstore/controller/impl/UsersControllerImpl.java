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
import java.util.List;

@WebServlet("/users")
public class UsersControllerImpl extends HttpServlet {
    private final DataBaseManager manager = new DataBaseManager();
    private final UserService userService = new UserServiceImpl(new UserDaoImpl(manager));
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<UserDto> users;
        String rawLastName = req.getParameter("lastname");
        if (rawLastName == null) {
            users = userService.getAll();
        } else {
            String lastName = reformatLastName(rawLastName);
            users = userService.getByLastName(lastName);
        }
        req.setAttribute("users", users);
        req.getRequestDispatcher("jsp/users.jsp").forward(req, resp);
    }

    private String reformatLastName(String rawLastName) {
        return rawLastName.replace("%20", " ");
    }
}
