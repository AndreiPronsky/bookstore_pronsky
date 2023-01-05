package online.javaclass.bookstore.controller.command.impl.bookCommands;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.controller.FrontController;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;

@Log4j2
@RequiredArgsConstructor
public class EditBookCommand implements Command {

    private final BookService bookService;

    @Override
    public String execute(HttpServletRequest req) {
        BookDto book = BookCommandUtils.setBookParameters(req);
        book.setId(Long.parseLong(req.getParameter("id")));
        BookDto updatedBook = bookService.update(book);
        req.setAttribute("book", updatedBook);
        String page = "controller?command=book&id=" + updatedBook.getId();
        return FrontController.REDIRECT + page;
    }
}
