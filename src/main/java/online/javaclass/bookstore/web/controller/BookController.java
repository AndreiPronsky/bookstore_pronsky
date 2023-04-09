package online.javaclass.bookstore.web.controller;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;
import online.javaclass.bookstore.service.dto.PageableDto;
import online.javaclass.bookstore.service.dto.UserDto;
import online.javaclass.bookstore.web.filter.SecurityCheck;
import online.javaclass.bookstore.web.utils.PagingUtil;
import org.springframework.http.HttpStatus;
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
    public String getAll(@RequestParam String page, @RequestParam String page_size, Model model) {
        PageableDto pageable = pagingUtil.getPageable(page, page_size);
        List<BookDto> books = service.getAll();
        model.addAttribute("page", pageable.getPage());
        model.addAttribute("total_pages", pageable.getTotalPages());
        model.addAttribute("books", books);
        return "books";
    }

    @LogInvocation
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityCheck(allowed = {UserDto.Role.MANAGER})
    public String add(@ModelAttribute BookDto book, Model model) {
//        try {
        BookDto created = service.save(book);
        model.addAttribute("book", created);
        //           uploadImage(image, created.getId());
        return "book";
//        } catch (IOException e) {
//            return "/error";
//        }
    }

    @LogInvocation
    @GetMapping("/add")
    @SecurityCheck(allowed = {UserDto.Role.MANAGER})
    public String addForm() {
        return "add_book";
    }

    @LogInvocation
    @PostMapping("/edit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @SecurityCheck(allowed = {UserDto.Role.MANAGER})
    public String edit(@ModelAttribute BookDto book, Model model) {
        BookDto updated = service.save(book);
        model.addAttribute("book", updated);
        return "book";
    }

    @LogInvocation
    @GetMapping("/edit/{id}")
    @SecurityCheck(allowed = {UserDto.Role.MANAGER})
    public String editForm(@PathVariable Long id, Model model) {
        BookDto book = service.getById(id);
        model.addAttribute("book", book);
        return "edit_book";
    }

    private void uploadImage(Part image, Long bookId) throws IOException {
        Properties properties = new Properties();
        try (InputStream input = this.getClass().getResourceAsStream(PATH_TO_PROPS)) {
            properties.load(input);
            String coverImageUploadDir = properties.getProperty("cover.upload.dir");
            String fileName = bookId + ".png";
            image.write(coverImageUploadDir + fileName);
        }
    }
}
