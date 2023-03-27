package online.javaclass.bookstore.controller;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.utils.BookControllerUtils;
import online.javaclass.bookstore.controller.utils.PagingUtil;
import online.javaclass.bookstore.exceptions.ValidationException;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;
import online.javaclass.bookstore.service.dto.PageableDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private static final String PATH_TO_PROPS = "application.properties";
    private final BookService service;
//    private final BookControllerUtils bookControllerUtils;
    private final PagingUtil pagingUtil;

    @LogInvocation
    @GetMapping(path = "/{id}")
    public String getById(@PathVariable Long id, Model model) {
        BookDto book = service.getById(id);
        model.addAttribute("book", book);
        return "book";
    }

    @LogInvocation
    @GetMapping(path = "/all")
    public String getAll(Model model) {
        List<BookDto> books;
        PageableDto pageable = pagingUtil.getPageable(model);
        books = service.getAll(pageable);
        model.addAttribute("page", pageable.getPage());
        model.addAttribute("total_pages", pageable.getTotalPages());
        model.addAttribute("books", books);
        return "books";
    }

    @LogInvocation
    @PostMapping(path = "/add")
    public String create(@ModelAttribute BookDto book, @RequestPart Part image, Model model) {
        String page;
        try {
            BookDto createdBook = service.create(book);
            if (image != null) {
                Properties properties = new Properties();
                try (InputStream input = this.getClass().getResourceAsStream(PATH_TO_PROPS)) {
                    properties.load(input);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String coverImageUploadDir = properties.getProperty("cover.upload.dir");
                String fileName = createdBook.getId().toString() + ".png";
                image.write(coverImageUploadDir + fileName);
            }
            page = "/books/" + createdBook.getId();
        } catch (ValidationException e) {
            model.addAttribute("validationMessages", e.getMessages());
            page = "add_book";
        }
        catch (IOException e) {
            throw new RuntimeException("Unable to upload image");
        }
        return FrontController.REDIRECT + page;
    }

    @LogInvocation
    @GetMapping(path = "/add")
    public String createForm() {
        return "add_book";
    }

    @LogInvocation
    @PostMapping(path = "/edit/{id}")
    public String updateBook(@ModelAttribute BookDto book, Model model) {
        String page;
        try {
            BookDto updatedBook = service.update(book);
            page = "/books/" + updatedBook.getId();
        } catch (ValidationException e) {
            model.addAttribute("validationMessages", e.getMessages());
            page = "/edit_book/" + book.getId();
        }
        return FrontController.REDIRECT + page;
    }

    @LogInvocation
    @GetMapping(path = "/edit/{id}")
    public String updateBookForm(@PathVariable Long id, Model model) {
        BookDto book = service.getById(id);
        model.addAttribute("book", book);
        return "edit_book";
    }
}
