package online.javaclass.bookstore.controller.utils;

import online.javaclass.bookstore.exceptions.ValidationException;
import online.javaclass.bookstore.platform.MessageManager;
import online.javaclass.bookstore.service.dto.BookDto;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

//@Component
public class BookControllerUtils {
    private static final String PATH_TO_PROPS = "/static/application.properties";
    private final String authorValidationRegex;
    private final String isbnValidationRegex;
    private final String titleValidationRegex;
    private final String punctuationMarksSequence;
    private final String coverImageUploadDir;
    MessageManager messageManager;

    public String getCoverImageUploadDir() {
        return coverImageUploadDir;
    }

    public BookControllerUtils() {
        Properties properties = new Properties();
        try (InputStream input = this.getClass().getResourceAsStream(PATH_TO_PROPS)) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        authorValidationRegex = properties.getProperty("author.validation.regexp");
        isbnValidationRegex = properties.getProperty("isbn.validation.regexp");
        titleValidationRegex = properties.getProperty("title.validation.regexp");
        punctuationMarksSequence = properties.getProperty("punctuation.marks.sequence.regexp");
        coverImageUploadDir = properties.getProperty("cover.upload.dir");
    }

    public BookDto setBookParameters(HttpServletRequest req) throws ValidationException {
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

    public Map<String, String> validate(HttpServletRequest req) throws ValidationException {
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

    public String validateAndReformatTitle(String rawTitle, List<String> messages) {
        if (rawTitle == null || rawTitle.isBlank() || !rawTitle.matches(titleValidationRegex)
                || (rawTitle.matches(punctuationMarksSequence) && !rawTitle.matches("[\\d.,]+"))) {
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

    public String validateAndReformatAuthor(String rawAuthor, List<String> messages) {
        String author;
        if (rawAuthor == null || rawAuthor.isBlank() || !rawAuthor.matches(authorValidationRegex)
                || rawAuthor.matches(punctuationMarksSequence)) {
            messages.add(messageManager.getMessage("error.invalid_author"));
            author = null;
        } else {
            author = rawAuthor.trim();
            author = StringUtils.capitalize(author);
            author = StringUtils.replace(author, "\\s{2,}", " ");
        }
        return author;
    }

    public String validateAndReformatIsbn(String rawIsbn, List<String> messages) {
        String isbn;
        rawIsbn = StringUtils.replace(rawIsbn, " ", "");
        if (rawIsbn == null || !rawIsbn.matches(isbnValidationRegex)) {
            messages.add(messageManager.getMessage("error.invalid_isbn"));
            isbn = null;
        } else {
            isbn = rawIsbn.trim();
        }
        return isbn;
    }

    public String validateAndReformatGenre(String rawGenre, List<String> messages) {
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

    public String validateAndReformatCover(String rawCover, List<String> messages) {
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

    public String validateAndReformatRating(String rawRating, List<String> messages) {
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

    public String validateAndReformatPages(String rawPages, List<String> messages) {
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

    public String validateAndReformatPrice(String rawPrice, List<String> messages) {
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
