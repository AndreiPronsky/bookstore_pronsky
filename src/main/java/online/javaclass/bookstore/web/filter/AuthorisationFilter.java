package online.javaclass.bookstore.web.filter;

import online.javaclass.bookstore.service.dto.UserDto;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
