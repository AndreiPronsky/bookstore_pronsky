package online.javaclass.bookstore.controller.command.impl.bookCommands;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;

import java.math.BigDecimal;

@Log4j2
@RequiredArgsConstructor
public class EditBookCommand implements Command {

    private final BookService bookService;

    @Override
    public String execute(HttpServletRequest req) {
        BookDto book = new BookDto();
        book.setId(Long.parseLong(req.getParameter("id")));
        book.setTitle(req.getParameter("title"));
        book.setAuthor(req.getParameter("author"));
        book.setIsbn(req.getParameter("isbn"));
        book.setGenre(BookDto.Genre.valueOf(req.getParameter("genre")));
        book.setCover(BookDto.Cover.valueOf(req.getParameter("cover")));
        book.setPages(Integer.valueOf(req.getParameter("pages")));
        book.setRating(BigDecimal.valueOf(Double.parseDouble(req.getParameter("rating"))));
        book.setPrice(BigDecimal.valueOf(Double.parseDouble(req.getParameter("price"))));
        BookDto updatedBook = bookService.update(book);
        req.setAttribute("book", updatedBook);
        return "jsp/book.jsp";
    }
}
