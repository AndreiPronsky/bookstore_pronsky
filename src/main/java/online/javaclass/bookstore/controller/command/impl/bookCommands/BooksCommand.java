package online.javaclass.bookstore.controller.command.impl.bookCommands;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.controller.PagingUtil;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;
import online.javaclass.bookstore.service.dto.PageableDto;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class  BooksCommand implements Command {
    private final BookService bookService;

    @Override
    public String execute(HttpServletRequest req) {
        List<BookDto> books;
        try {
            PageableDto pageable = PagingUtil.getPageable(req);
            books = bookService.getAll(pageable);
            req.setAttribute("page", pageable.getPage());
            req.setAttribute("total_pages", pageable.getTotalPages());
            req.setAttribute("books", books);
            return "jsp/books.jsp";
        } catch (Exception e) {
            log.error(e.getClass() + " " + e.getMessage());
            return "jsp/error.jsp";
        }
    }
}

