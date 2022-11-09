package online.javaclass.bookstore.controller.impl;

import online.javaclass.bookstore.controller.Controller;
import online.javaclass.bookstore.data.entities.Cover;
import online.javaclass.bookstore.data.entities.Role;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.List;

public class BookControllerImpl implements Controller {
    private final BookService bookService;

    public BookControllerImpl(BookService bookService) {
        this.bookService = bookService;
    }

    public void process(String request, PrintStream response) {

        if (request.startsWith("get all")) {
            getAll(response);
        } else if (request.startsWith("create ")) {
            create(request, response);
        } else if (request.startsWith("update ")) {
            update(request, response);
        } else if (request.startsWith("delete ")) {
            delete(request, response);
        } else if (request.matches("^get \\d+$")) {
            getById(request, response);
        } else if (request.matches("^get [a-zA-Z ]+$")) {
            getByAuthor(request, response);
        } else if (request.matches("^get [\\d+-]+$")) {
            getByIsbn(request, response);
        } else {
            response.println("UNKNOWN COMMAND!");
        }
    }

    private void getById(String request, PrintStream response) {
        Long id = Long.parseLong(request.split(" ")[1]);
        BookDto book = bookService.getById(id);
        response.println("BOOK : ");
        response.println(book);
    }

    private void getAll(PrintStream response) {
        List<BookDto> books = bookService.getAll();
        response.println("ALL BOOKS : ");
        for (BookDto book : books) {
            response.println(book);
        }
        response.println("END OF LIST");
    }

    private void create(String request, PrintStream response) {
        String[] data = request.substring(7).split(", ");
        String title = data[0];
        String author = data[1];
        String isbn = data[2];
        Cover cover = getCoverFromRequest(data[3]);
        int pages = Integer.parseInt(data[4]);
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(data[5]));
        BigDecimal rating = BigDecimal.valueOf(Double.parseDouble(data[6]));
        BookDto book = new BookDto(title, author, isbn, cover, pages, price, rating);
        bookService.create(book);
        response.println("CREATED BOOK : ");
        response.println(book);
    }

    private void getByIsbn(String request, PrintStream response) {
        String isbn = request.substring(4);
        BookDto book = bookService.getByIsbn(isbn);
        response.println("BOOK : ");
        response.println(book);
    }

    private void getByAuthor(String request, PrintStream response) {
        String author = request.substring(4);
        List<BookDto> books = bookService.getByAuthor(author);
        response.println("BOOK WRITTEN BY " + "\"" + author + "\"" + " :");
        for (BookDto book : books) {
            response.println(book);
        }
        response.println("END OF LIST");
    }

    private void update(String request, PrintStream response) {
        String[] data = request.substring(7).split(", ");
        Long id = Long.parseLong(data[0]);
        String title = data[1];
        String author = data[2];
        String isbn = data[3];
        Cover cover = getCoverFromRequest(data[4]);
        int pages = Integer.parseInt(data[5]);
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(data[6]));
        BigDecimal rating = BigDecimal.valueOf(Double.parseDouble(data[7]));
        BookDto book = new BookDto(id, title, author, isbn, cover, pages, price, rating);
        bookService.update(book);
        response.println("UPDATED BOOK WITH ID : " + id);
    }

    private void delete(String request, PrintStream response) {
        Long id = Long.parseLong(request.split(" ")[1]);
        bookService.deleteById(id);
        response.println("DELETED BOOK WITH ID : " + id);
    }

    private Cover getCoverFromRequest(String verifyCover) {
        Cover cover = null;
        switch (verifyCover) {
            case "hard" -> cover = Cover.HARD;
            case "soft" -> cover = Cover.SOFT;
            case "special" -> cover = Cover.SPECIAL;
        }
        return cover;
    }
}
