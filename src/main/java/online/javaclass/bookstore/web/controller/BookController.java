package online.javaclass.bookstore.web.controller;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;
import online.javaclass.bookstore.service.dto.UserDto;
import online.javaclass.bookstore.web.filter.SecurityCheck;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private static final String PATH_TO_PROPS = "application.properties";
    private final BookService service;

    @LogInvocation
    @GetMapping("/{id}")
    public String getOne(@PathVariable Long id, Model model) {
        BookDto book = service.getById(id);
        model.addAttribute("book", book);
        return "book";
    }

    @LogInvocation
    @GetMapping("/all")
    public String getAll(Pageable pageable, Model model) {
        Page<BookDto> page = service.getAll(pageable);
        model.addAttribute("page", page.getNumber());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("books", page.stream().toList());
        return "books";
    }

    @LogInvocation
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityCheck(allowed = {UserDto.Role.MANAGER})
    public String add(@ModelAttribute @Valid BookDto book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            addMessagesToModel(result, model);
            return "add_book";
        }
        BookDto created = service.save(book);
        model.addAttribute("book", created);
        return "book";

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
    public String edit(@ModelAttribute @Valid BookDto book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            addMessagesToModel(result, model);
            return "edit_book";
        }
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

    private static void addMessagesToModel(BindingResult result, Model model) {
        List<String> errorDescriptions = new ArrayList<>();
        for (ObjectError error : result.getAllErrors()) {
            errorDescriptions.add(error.getDefaultMessage());
        }
        model.addAttribute("validationMessages", errorDescriptions);
    }
}
