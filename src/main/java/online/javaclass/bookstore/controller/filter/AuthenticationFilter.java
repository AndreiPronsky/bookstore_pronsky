package online.javaclass.bookstore.controller.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import online.javaclass.bookstore.service.impl.RestrictedCommandList;

import java.io.IOException;

public class AuthenticationFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String command = req.getParameter("command");
        if (RestrictedCommandList.requiresAuthentication(command)) {
            HttpSession session = req.getSession();
            if (command.equals("logout")) {
                chain.doFilter(req, res);
            }
            if (session.getAttribute("user") == null) {
                req.getRequestDispatcher("jsp/error.jsp").forward(req, res);
                return;
            }
        }
        chain.doFilter(req, res);
    }
}
