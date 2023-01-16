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
/**
 * The command is used to set books as attribute of HttpServletRequest for further creation of book catalogue
 * page on clients side.
 * @author Andrei Pronsky
 */
@Log4j2
@RequiredArgsConstructor
public class BooksCommand implements Command {
    private final BookService bookService;
    /**
     * Takes input parameters from the HttpServletRequest, and sends it to service layer to get all existing books
     * meeting the requirements of number of current page in the catalogue.
     * @param req - request from client to get page information for pagination.
     * @return jsp page with pre-set books according to a page size and page number
     */
    @Override
    public String execute(HttpServletRequest req) {
        List<BookDto> books;
        PageableDto pageable = PagingUtil.getPageable(req);
        books = bookService.getAll(pageable);
        req.setAttribute("page", pageable.getPage());
        req.setAttribute("total_pages", pageable.getTotalPages());
        req.setAttribute("books", books);
        return "jsp/books.jsp";
    }
}

