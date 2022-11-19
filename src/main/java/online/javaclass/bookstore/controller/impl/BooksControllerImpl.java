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
import java.util.List;

@WebServlet("/books")
public class BooksControllerImpl extends HttpServlet {
    private final static Logger log = LogManager.getLogger();
    private final DataBaseManager manager = new DataBaseManager();
    private final BookService bookService = new BookServiceImpl(new BookDaoImpl(manager));
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<BookDto> books;
        String rawAuthor = req.getParameter("author");
        if (rawAuthor == null) {
            books = bookService.getAll();
        } else {
            String author = reformatAuthor(rawAuthor);
            books = bookService.getByAuthor(author);
        }
        if (books.isEmpty()) {
            resp.sendError(404);
        }
        renderHTML(resp, books);
    }

    private String reformatAuthor(String rawAuthor) {
        return rawAuthor.replace("%20", " ");
    }

    private void renderHTML(HttpServletResponse resp, List<BookDto> books) throws IOException {
        PrintWriter writer = resp.getWriter();
        writer.println("<html>");
        printTableHead(writer);
        printContent(books, writer);
        printTableFooter(writer);
        writer.println("</html>");
    }

    private void printContent(List<BookDto> books, PrintWriter writer) {
        for (BookDto book: books) {
            printBookData(book, writer);
        }
    }

    private void printTableFooter(PrintWriter writer) {
        writer.println("</tbody>");
        writer.println("</table>");
    }

    private void printTableHead(PrintWriter writer) {
        writer.println("<table>");
        writer.println("<caption>Books</caption>");
        writer.println("<thead>");
        writer.println("<td> Author </td>");
        writer.println("<td> Title </td>");
        writer.println("<td> Price </td>");
        writer.println("</thead>");
        writer.println("<tbody>");
    }

    private void printBookData(BookDto book, PrintWriter writer) {
        writer.println("<tr><th>" + book.getAuthor() + "</th><th>" + book.getTitle()
                + "</th><th>" + book.getPrice() + "</th></tr>");
    }
}
