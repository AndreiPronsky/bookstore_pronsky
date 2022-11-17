package online.javaclass.bookstore.controller.impl;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import online.javaclass.bookstore.data.connection.DataBaseManager;
import online.javaclass.bookstore.data.dao.impl.BookDaoImpl;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;
import online.javaclass.bookstore.service.impl.BookServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/book")
public class BookControllerImpl extends HttpServlet {
    private final DataBaseManager manager = new DataBaseManager();
    private final BookService bookService = new BookServiceImpl(new BookDaoImpl(manager));
    //private static final Logger log = LogManager.getLogger();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String rawId = req.getParameter("id");
        Long id = Long.parseLong(rawId);
        BookDto book = bookService.getById(id);
        PrintWriter writer = resp.getWriter();
            writer.println("<html>");
            writer.println("<h1>Book :</h1>");
            writer.println("<h2> Author : " + book.getAuthor() + "</h2>");
            writer.println("<h2> Title : " + book.getTitle() + "</h2>");
            writer.println("<p> Price : " + book.getPrice() + "</p>");
            writer.println("</html>");

    }
}
