package online.javaclass.bookstore.controller.command.impl.bookCommands;

import jakarta.servlet.http.HttpServletRequest;
import online.javaclass.bookstore.service.dto.BookDto;

import java.math.BigDecimal;

public class BookCommandUtils {
    static BookDto setBookParameters(HttpServletRequest req) {
        BookDto book = new BookDto();
        book.setTitle(req.getParameter("title"));
        book.setAuthor(req.getParameter("author"));
        book.setIsbn(req.getParameter("isbn"));
        book.setGenre(BookDto.Genre.valueOf(req.getParameter("genre")));
        book.setCover(BookDto.Cover.valueOf(req.getParameter("cover")));
        book.setPages(Integer.valueOf(req.getParameter("pages")));
        book.setRating(BigDecimal.valueOf(Double.parseDouble(req.getParameter("rating"))));
        book.setPrice(BigDecimal.valueOf(Double.parseDouble(req.getParameter("price"))));
        return book;
    }
}
