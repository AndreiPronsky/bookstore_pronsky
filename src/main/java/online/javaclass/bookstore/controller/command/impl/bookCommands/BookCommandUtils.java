package online.javaclass.bookstore.controller.command.impl.bookCommands;

import jakarta.servlet.http.HttpServletRequest;
import online.javaclass.bookstore.MessageManager;
import online.javaclass.bookstore.exceptions.ValidationException;
import online.javaclass.bookstore.service.dto.BookDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BookCommandUtils {
    private static final String AUTHOR_VALIDATION_REGEX = "^[\\w'\\-,.][^0-9_!¡?÷?¿\\/+=@#$%ˆ&*(){}|~<>;:]{2,}$";
    private static final String ISBN_VALIDATION_REGEX = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$";
    private static final String TITLE_VALIDATION_REGEX = "^[A-Za-z0-9\\s\\-_,.:#;()'\"]+$";
    private static final String PUNCTUATION_MARKS_SEQUENCE = "[.,\\/#!$%^&*;:{}=_`~()-<>]{2,}";
    MessageManager messageManager;

    BookDto setBookParameters(HttpServletRequest req) throws ValidationException {
        Map<String, String> params = validate(req);
        BookDto book = new BookDto();
        book.setTitle(params.get("title"));
        book.setAuthor(params.get("author"));
        book.setIsbn(params.get("isbn"));
        book.setGenre(BookDto.Genre.valueOf(params.get("genre")));
        book.setCover(BookDto.Cover.valueOf(params.get("cover")));
        book.setPages(Integer.valueOf(params.get("pages")));
        book.setRating(BigDecimal.valueOf(Double.parseDouble(params.get("rating"))));
        book.setPrice(BigDecimal.valueOf(Double.parseDouble(params.get("price"))));
        return book;
    }

    Map<String, String> validate(HttpServletRequest req) throws ValidationException {
        List<String> messages = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        params.put("title", validateAndReformatTitle(req.getParameter("title"), messages));
        params.put("author", validateAndReformatAuthor(req.getParameter("author"), messages));
        params.put("isbn", validateAndReformatIsbn(req.getParameter("isbn"), messages));
        params.put("genre", validateAndReformatGenre(req.getParameter("genre"), messages));
        params.put("cover", validateAndReformatCover(req.getParameter("cover"), messages));
        params.put("rating", validateAndReformatRating(req.getParameter("rating"), messages));
        params.put("pages", validateAndReformatPages(req.getParameter("pages"), messages));
        params.put("price", validateAndReformatPrice(req.getParameter("price"), messages));
        if (!messages.isEmpty()) {
            throw new ValidationException(messages);
        }
        return params;
    }

    String validateAndReformatTitle(String rawTitle, List<String> messages) {
        if (rawTitle == null || rawTitle.isBlank() || !rawTitle.matches(TITLE_VALIDATION_REGEX)
                || (rawTitle.matches(PUNCTUATION_MARKS_SEQUENCE) && !rawTitle.matches("[\\d.,]+"))) {
            messages.add(messageManager.getMessage("error.invalid_title"));
            return null;
        } else {
            rawTitle = rawTitle.trim();
            rawTitle = StringUtils.capitalize(rawTitle);
            rawTitle = StringUtils.replace(rawTitle, "\\s{2,}", " ");
            rawTitle = StringUtils.replace(rawTitle, "_", " ");
            return rawTitle;
        }
    }

    String validateAndReformatAuthor(String rawAuthor, List<String> messages) {
        String author;
        if (rawAuthor == null || rawAuthor.isBlank() || !rawAuthor.matches(AUTHOR_VALIDATION_REGEX)
                || rawAuthor.matches(PUNCTUATION_MARKS_SEQUENCE)) {
            messages.add(messageManager.getMessage("error.invalid_author"));
            author = null;
        } else {
            author = rawAuthor.trim();
            author = StringUtils.capitalize(author);
            author = StringUtils.replace(author, "\\s{2,}", " ");
        }
        return author;
    }

    String validateAndReformatIsbn(String rawIsbn, List<String> messages) {
        String isbn;
        rawIsbn = StringUtils.replace(rawIsbn, " ", "");
        if (rawIsbn == null || !rawIsbn.matches(ISBN_VALIDATION_REGEX)) {
            messages.add(messageManager.getMessage("error.invalid_isbn"));
            isbn = null;
        } else {
            isbn = rawIsbn.trim();
        }
        return isbn;
    }

    String validateAndReformatGenre(String rawGenre, List<String> messages) {
        if (rawGenre == null) {
            messages.add(messageManager.getMessage("error.invalid_genre"));
        } else {
            try {
                BookDto.Genre.valueOf(rawGenre.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                messages.add(messageManager.getMessage("error.invalid_genre"));
                return null;
            }
        }
        return rawGenre;
    }

    String validateAndReformatCover(String rawCover, List<String> messages) {
        if (rawCover == null) {
            messages.add(messageManager.getMessage("error.invalid_cover"));
        } else {
            try {
                BookDto.Cover.valueOf(rawCover);
            } catch (IllegalArgumentException e) {
                messages.add(messageManager.getMessage("error.invalid_cover"));
                return null;
            }
        }
        return rawCover;
    }

    String validateAndReformatRating(String rawRating, List<String> messages) {
        rawRating = StringUtils.replace(rawRating, ",", ".");
        try {
            BigDecimal rating = BigDecimal.valueOf(Double.parseDouble(rawRating));
            if (rating.compareTo(BigDecimal.ZERO) < 0 ||
                    rating.compareTo(BigDecimal.valueOf(5)) > 0) {
                messages.add(messageManager.getMessage("error.invalid_rating"));
                return null;
            } else {
                return rawRating;
            }
        } catch (NumberFormatException e) {
            messages.add(messageManager.getMessage("error.invalid_rating"));
            return null;
        }
    }

    String validateAndReformatPages(String rawPages, List<String> messages) {
        rawPages = StringUtils.replace(rawPages, " ", "");
        try {
            int pages = Integer.parseInt(rawPages);
            if (pages < 1) {
                return null;
            } else {
                return rawPages;
            }
        } catch (NumberFormatException e) {
            messages.add(messageManager.getMessage("error.invalid_pages"));
            return null;
        }
    }

    String validateAndReformatPrice(String rawPrice, List<String> messages) {
        rawPrice = StringUtils.replace(rawPrice, ",", ".");
        try {
            BigDecimal price = BigDecimal.valueOf(Double.parseDouble(rawPrice));
            if (price.compareTo(BigDecimal.ZERO) <= 0) {
                messages.add(messageManager.getMessage("error.invalid_price"));
                return null;
            } else {
                return rawPrice;
            }
        } catch (NumberFormatException e) {
            messages.add(messageManager.getMessage("error.invalid_price"));
            return null;
        }
    }
}
