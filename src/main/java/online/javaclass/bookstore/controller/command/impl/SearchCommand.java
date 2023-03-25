package online.javaclass.bookstore.controller.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;
import org.springframework.stereotype.Controller;

import java.util.List;

@RequiredArgsConstructor
@Controller("search")
public class SearchCommand implements Command {
    private final BookService bookService;

    @LogInvocation
    @Override
    public String execute(HttpServletRequest req) {
        String input = req.getParameter("search");
        List<BookDto> searchResult = bookService.search(input);
        req.setAttribute("books", searchResult);
        return "jsp/books.jsp";
    }
}
