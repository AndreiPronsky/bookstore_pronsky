package online.javaclass.bookstore.controller;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.utils.PagingUtil;
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
    private final PagingUtil pagingUtil;

    @LogInvocation
    @GetMapping("/{id}")
    public String getOne(@PathVariable Long id, Model model) {
        BookDto book = service.getById(id);
        model.addAttribute("book", book);
        return "book";
    }

    @LogInvocation
    @GetMapping("/all")
    public String getAll(Model model) {
        PageableDto pageable = pagingUtil.getPageable(model);
        List<BookDto> books = service.getAll(pageable);
        model.addAttribute("page", pageable.getPage());
        model.addAttribute("total_pages", pageable.getTotalPages());
        model.addAttribute("books", books);
        return "books";
    }

    @LogInvocation
    @PostMapping("/add")
    public String add(@ModelAttribute BookDto book, @RequestPart Part image, Model model) {
        try {
            BookDto created = service.create(book);
            model.addAttribute("book", created);
            uploadImage(image, created.getId());
            return "redirect: /" + created.getId();
        } catch (IOException e) {
            throw new RuntimeException("Unable to upload image");
        }
    }


    @LogInvocation
    @GetMapping("/add")
    public String addForm() {
        return "add_book";
    }

    @LogInvocation
    @PostMapping("/edit/{id}")
    public String edit(@ModelAttribute BookDto book, Model model) {
        BookDto updated = service.update(book);
        model.addAttribute("book", updated);
        return "redirect: edit_book";
    }

    @LogInvocation
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        BookDto book = service.getById(id);
        model.addAttribute("book", book);
        return "edit_book";
    }

    private void uploadImage(Part image, Long bookId) throws IOException {
        if (image != null) {
            Properties properties = new Properties();
            try (InputStream input = this.getClass().getResourceAsStream(PATH_TO_PROPS)) {
                properties.load(input);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String coverImageUploadDir = properties.getProperty("cover.upload.dir");
            String fileName = bookId + ".png";
            image.write(coverImageUploadDir + fileName);
        }
    }
}
