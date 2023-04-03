package online.javaclass.bookstore.web.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@Component
//@Order(1)
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
                req.getRequestDispatcher("/error").forward(req, res);
                return;
            }
        }
        chain.doFilter(req, res);
    }
}
