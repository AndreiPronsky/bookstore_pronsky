package online.javaclass.bookstore.controller.command.impl.bookCommands;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.controller.FrontController;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.exceptions.ValidationException;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;
import org.springframework.stereotype.Controller;

@Log4j2
@RequiredArgsConstructor
@Controller("add_book")
public class AddBookCommand implements Command {
    private final BookService bookService;
    private final BookCommandUtils commandUtils;

    @Override
    public String execute(HttpServletRequest req) {
        String page;
        HttpSession session = req.getSession();
        session.removeAttribute("validationMessages");
        try {
            BookDto book = commandUtils.setBookParameters(req);
        BookDto newBook = bookService.create(book);
            req.setAttribute("book", newBook);
            page = "controller?command=book&id=" + newBook.getId();
        } catch (ValidationException e) {
            session.setAttribute("validationMessages", e.getMessages());
            page = "controller?command=add_book_form";
        }
        return FrontController.REDIRECT + page;
    }
}
