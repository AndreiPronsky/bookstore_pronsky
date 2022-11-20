package online.javaclass.bookstore.controller.impl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import online.javaclass.bookstore.data.connection.DataBaseManager;
import online.javaclass.bookstore.data.dao.impl.BookDaoImpl;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;
import online.javaclass.bookstore.service.impl.BookServiceImpl;

import java.io.IOException;

@WebServlet("/book")
public class BookControllerImpl extends HttpServlet {
    private final DataBaseManager manager = new DataBaseManager();
    private final BookService bookService = new BookServiceImpl(new BookDaoImpl(manager));
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
            Long id = processId(req);
            BookDto book = bookService.getById(id);
            req.setAttribute("book", book);
            req.getRequestDispatcher("jsp/book.jsp").forward(req, resp);
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
