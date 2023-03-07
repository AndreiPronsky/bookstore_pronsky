package online.javaclass.bookstore.controller.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import online.javaclass.bookstore.service.dto.UserDto;

import java.io.IOException;

public class AuthorisationFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String command = req.getParameter("command");
        HttpSession session = req.getSession();
        if (RestrictedCommandList.requiresAuthorisation(command)) {
            UserDto user = (UserDto) session.getAttribute("user");
            if (user.getRole().equals(UserDto.Role.USER)) {
                req.getRequestDispatcher("jsp/error.jsp").forward(req, res);
                return;
            }
        }
        chain.doFilter(req, res);
    }
}
