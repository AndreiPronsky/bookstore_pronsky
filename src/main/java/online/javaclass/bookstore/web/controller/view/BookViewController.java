package online.javaclass.bookstore.web.controller.view;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;
import online.javaclass.bookstore.service.dto.UserDto;
import online.javaclass.bookstore.web.filter.SecurityCheck;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookViewController {
    private final BookService service;

    @LogInvocation
    @GetMapping("/{id}")
    public String getOne(@PathVariable Long id, Model model) {
        BookDto book = service.getById(id);
        model.addAttribute("book", book);
        return "book/book";
    }

    @LogInvocation
    @GetMapping("/search")
    public String search(@RequestParam String search, Model model) {
        List<BookDto> searchResult = service.search(search);
        model.addAttribute("books", searchResult);
        return "book/books";
    }

    @LogInvocation
    @GetMapping("/all")
    public String getAll(Model model, @SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<BookDto> page = service.getAll(pageable);
        model.addAttribute("page", page.getNumber());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("books", page.stream().toList());
        return "book/books";
    }

    @LogInvocation
    @GetMapping("/add")
    @SecurityCheck(allowed = {UserDto.Role.MANAGER})
    public String addForm(Model model) {
        model.addAttribute("bookDto", new BookDto());
        return "add_book";
    }

    @LogInvocation
    @GetMapping("/edit/{id}")
    @SecurityCheck(allowed = {UserDto.Role.MANAGER})
    public String editForm(@PathVariable Long id, Model model) {
        BookDto book = service.getById(id);
        model.addAttribute("bookDto", book);
        return "book/edit_book";
    }

    @LogInvocation
    @GetMapping("/delete/{id}")
    @SecurityCheck(allowed = {UserDto.Role.MANAGER})
    public String delete(@PathVariable Long id, Model model) {
        BookDto book = service.getById(id);
        model.addAttribute("book", book);
        return "book/edit_book";
    }
}
