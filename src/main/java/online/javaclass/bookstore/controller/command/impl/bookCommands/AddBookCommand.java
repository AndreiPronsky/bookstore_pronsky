package online.javaclass.bookstore.controller.command.impl.bookCommands;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.FrontController;
import online.javaclass.bookstore.controller.command.Command;
import online.javaclass.bookstore.controller.utils.BookControllerUtils;
import online.javaclass.bookstore.exceptions.ValidationException;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;

/**
 * The command is used to create a new book
 *
 * @author Andrei Pronsky
 */
@RequiredArgsConstructor
//@Controller("add_book")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class AddBookCommand implements Command {
    private static final String COVER_UPLOAD_DIR =
            "C:\\Repository\\bookstore\\bookstore_pronsky\\src\\main\\webapp\\coverImages\\";
    private final BookService bookService;
    private final BookControllerUtils commandUtils;

    /**
     * Takes input parameters from the HttpServletRequest, and sends it to service layer if validation is passed.
     *
     * @param req - request from client to get parameters to work with
     * @return If input parameters match the requirements sends user to a created book page.
     * If input parameters don't match requirements, redirects user to add_book.jsp with description of what exactly
     * went wrong.
     */
    @LogInvocation
    @Override
    public String execute(HttpServletRequest req) {
        String page;
        HttpSession session = req.getSession();
        session.removeAttribute("validationMessages");
        try {
            BookDto book = commandUtils.setBookParameters(req);
            BookDto createdBook = bookService.create(book);
            req.setAttribute("book", createdBook);
            Part filePart = req.getPart("image");
            if (filePart != null) {
                String fileName = createdBook.getId().toString() + ".png";
                filePart.write(COVER_UPLOAD_DIR + fileName);
            }
            page = "controller?command=book&id=" + createdBook.getId();
        } catch (ValidationException e) {
            session.setAttribute("validationMessages", e.getMessages());
            page = "controller?command=add_book_form";
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
        return FrontController.REDIRECT + page;
    }
}
