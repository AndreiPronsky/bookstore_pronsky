package online.javaclass.bookstore.controller.command.impl.bookCommands;

import jakarta.servlet.http.HttpServletRequest;
import online.javaclass.bookstore.MessageManager;
import online.javaclass.bookstore.exceptions.ValidationException;
import online.javaclass.bookstore.service.dto.BookDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BookCommandUtils {
    private static final String AUTHOR_VALIDATION_REGEX = "^[\\w'\\-,.][^0-9_!¡?÷?¿\\/+=@#$%ˆ&*(){}|~<>;:]{2,}$";
    private static final String ISBN_VALIDATION_REGEX = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$";
    MessageManager messageManager = MessageManager.INSTANCE;
     BookDto setBookParameters(HttpServletRequest req) throws ValidationException{
        validate(req);
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

    private void validate(HttpServletRequest req) throws ValidationException {
        List<String> messages = new ArrayList<>();
        if (req.getParameter("title") == null || req.getParameter("title").isBlank()) {
            messages.add(messageManager.getMessage("error.invalid_title"));
        }
        if (req.getParameter("author") == null || req.getParameter("author").isBlank() ||
        !req.getParameter("author").matches(AUTHOR_VALIDATION_REGEX)) {
            messages.add(messageManager.getMessage("error.invalid_author"));
        }
        if (req.getParameter("isbn") == null || !req.getParameter("isbn").matches(ISBN_VALIDATION_REGEX)) {
            messages.add(messageManager.getMessage("error.invalid_isbn"));
        }
        if (req.getParameter("genre") == null) {
            messages.add(messageManager.getMessage("error.invalid_genre"));
        }
        try {
            BookDto.Genre.valueOf(req.getParameter("genre"));
        } catch (IllegalArgumentException e) {
            messages.add(messageManager.getMessage("error.invalid_genre"));
        }
        if (req.getParameter("cover") == null) {
            messages.add(messageManager.getMessage("error.invalid_cover"));
        }
        try {
            BookDto.Cover.valueOf(req.getParameter("cover"));
        } catch (IllegalArgumentException e) {
            messages.add(messageManager.getMessage("error.invalid_cover"));
        }
        if (BigDecimal.valueOf(Double.parseDouble(req.getParameter("rating"))).compareTo(BigDecimal.ZERO) < 0 ||
                BigDecimal.valueOf(Double.parseDouble(req.getParameter("rating"))).compareTo(BigDecimal.valueOf(5)) > 0) {
            messages.add(messageManager.getMessage("error.invalid_rating"));
        }
        if (Integer.parseInt(req.getParameter("pages")) < 1) {
            messages.add(messageManager.getMessage("error.invalid_pages"));
        }
        if (BigDecimal.valueOf(Double.parseDouble(req.getParameter("price"))).compareTo(BigDecimal.ZERO) < 0) {
            messages.add(messageManager.getMessage("error.invalid_price"));
        }
        if (!messages.isEmpty()) {
            throw new ValidationException(messages);
        }
    }
}
