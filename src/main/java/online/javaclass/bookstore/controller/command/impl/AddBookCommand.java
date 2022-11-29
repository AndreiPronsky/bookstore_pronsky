package online.javaclass.bookstore.controller.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;

import java.math.BigDecimal;

@Log4j2
@RequiredArgsConstructor
public class AddBookCommand implements Command {
    private final BookService bookService;
    @Override
    public String execute(HttpServletRequest req) {
        BookDto book = new BookDto();
        book.setTitle(req.getParameter("title"));
        book.setAuthor(req.getParameter("author"));
        book.setIsbn(req.getParameter("isbn"));
        book.setGenre(BookDto.Genre.values()[Integer.parseInt(req.getParameter("genre"))-1]);
        book.setCover(BookDto.Cover.values()[Integer.parseInt(req.getParameter("cover"))-1]);
        book.setPages(Integer.valueOf(req.getParameter("pages")));
        book.setRating(BigDecimal.valueOf(Double.parseDouble(req.getParameter("rating"))));
        book.setPrice(BigDecimal.valueOf(Double.parseDouble(req.getParameter("price"))));
        BookDto newBook = bookService.create(book);
        req.setAttribute("book", newBook);
        return "jsp/book.jsp";
    }
}
