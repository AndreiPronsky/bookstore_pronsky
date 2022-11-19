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

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/book")
public class BookControllerImpl extends HttpServlet {
    private final DataBaseManager manager = new DataBaseManager();
    private final BookService bookService = new BookServiceImpl(new BookDaoImpl(manager));
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Long id = processId(req);
            BookDto book = bookService.getById(id);
            renderHTML(resp, book);
        } catch (Exception e) {
            if (e instanceof NumberFormatException) {
                resp.sendError(400);
            } else if (e instanceof NullPointerException) {
                resp.sendError(404);
            }
        }
    }

    private void renderHTML(HttpServletResponse resp, BookDto book) throws IOException {
        PrintWriter writer = resp.getWriter();
        writer.println("<html>");
        printContent(book, writer);
        writer.println("</html>");
    }

    private void printContent(BookDto book, PrintWriter writer) {
        writer.println("<h1>Book :</h1>");
        writer.println("<h2> Author : " + book.getAuthor() + "</h2>");
        writer.println("<h2> Title : " + book.getTitle() + "</h2>");
        writer.println("<p> Price : " + book.getPrice() + "</p>");
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
