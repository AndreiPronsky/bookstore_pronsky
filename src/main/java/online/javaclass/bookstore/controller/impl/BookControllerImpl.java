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

//    private final BookService bookService;
//
//    public BookControllerImpl(BookService bookService) {
//        this.bookService = bookService;
//    }
//
//    public void process(String request, PrintStream response) {
//        if (request.startsWith("GET /get?all")) {
//            getAll(response);
//        } else if (request.startsWith("create ")) {
//            create(request, response);
//        } else if (request.startsWith("update ")) {
//            update(request, response);
//        } else if (request.matches("^delete \\d+$")) {
//            delete(request, response);
//        } else if (request.startsWith("GET /get?id=")) {
//            getById(request, response);
//        } else if (request.startsWith("GET /get?author=")) {
//            getByAuthor(request, response);
//        } else if (request.startsWith("GET /get?isbn=")) {
//            getByIsbn(request, response);
//        } else {
//            response.println("UNKNOWN COMMAND!");
//        }
//    }
//
//    public void getById(String request, PrintStream response) {
//        String idString = extractData(request);
//        Long id = Long.parseLong(idString);
//        BookDto book = bookService.getById(id);
//        response.println("HTTP/1.1 200 OK");
//        response.println();
//        response.println("Book : ");
//        response.println(book);
//        response.println();
//    }
//
//    public void getAll(PrintStream response) {
//        List<BookDto> books = bookService.getAll();
//        response.println("HTTP/1.1 200 OK");
//        response.println();
//        response.println("BOOKS : ");
//        for (BookDto book : books) {
//            response.println(book);
//        }
//        response.println();
//    }
//
//    public void create(String request, PrintStream response) {
//        String[] data = request.substring(7).split(", ");
//        String title = data[0];
//        String author = data[1];
//        String isbn = data[2];
//        Cover cover = getCoverFromRequest(data[3]);
//        int pages = Integer.parseInt(data[4]);
//        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(data[5]));
//        BigDecimal rating = BigDecimal.valueOf(Double.parseDouble(data[6]));
//        BookDto book = new BookDto(title, author, isbn, cover, pages, price, rating);
//        bookService.create(book);
//        response.println("CREATED BOOK : ");
//        response.println(book);
//    }
//
//    public void getByIsbn(String request, PrintStream response) {
//        String isbn = extractData(request);
//        BookDto book = bookService.getByIsbn(isbn);
//        response.println("HTTP/1.1 200 OK");
//        response.println();
//        response.println("BOOK : ");
//        response.println(book);
//        response.println();
//    }
//
//    public void getByAuthor(String request, PrintStream response) {
//        String author = extractData(request).replace("%20", " ");
//        List<BookDto> books = bookService.getByAuthor(author);
//        response.println("HTTP/1.1 200 OK");
//        response.println();
//        response.println("BOOK(S) WRITTEN BY " + "\"" + author + "\"" + " :");
//        for (BookDto book : books) {
//            response.println(book);
//        }
//        response.println();
//    }
//
//    private String extractData(String request) {
//        return request.substring(request.indexOf('=') + 1, request.indexOf('H')).trim();
//    }
//
//
//    public void update(String request, PrintStream response) {
//        String[] data = request.substring(7).split(", ");
//        Long id = Long.parseLong(data[0]);
//        String title = data[1];
//        String author = data[2];
//        String isbn = data[3];
//        Cover cover = getCoverFromRequest(data[4]);
//        int pages = Integer.parseInt(data[5]);
//        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(data[6]));
//        BigDecimal rating = BigDecimal.valueOf(Double.parseDouble(data[7]));
//        BookDto book = new BookDto(id, title, author, isbn, cover, pages, price, rating);
//        bookService.update(book);
//        response.println("UPDATED BOOK WITH ID : " + id);
//    }
//
//
//    public void delete(String request, PrintStream response) {
//        Long id = Long.parseLong(request.split(" ")[1]);
//        bookService.deleteById(id);
//        response.println("DELETED BOOK WITH ID : " + id);
//    }
//
//    private Cover getCoverFromRequest(String verifyCover) {
//        Cover cover = null;
//        switch (verifyCover) {
//            case "hard" -> cover = Cover.HARD;
//            case "soft" -> cover = Cover.SOFT;
//            case "special" -> cover = Cover.SPECIAL;
//        }
//        return cover;
//    }
}
