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
import java.util.List;

@WebServlet("/books")
public class BooksControllerImpl extends HttpServlet {
    private final DataBaseManager manager = new DataBaseManager();
    private final BookService bookService = new BookServiceImpl(new BookDaoImpl(manager));
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<BookDto> books;
        String rawAuthor = req.getParameter("author");
        if (rawAuthor == null) {
            books = bookService.getAll();
        } else {
            String author = reformatAuthor(rawAuthor);
            books = bookService.getByAuthor(author);
        }
        req.setAttribute("books", books);
        req.getRequestDispatcher("jsp/books.jsp").forward(req, resp);
    }

    private String reformatAuthor(String rawAuthor) {
        return rawAuthor.replace("%20", " ");
    }
}
