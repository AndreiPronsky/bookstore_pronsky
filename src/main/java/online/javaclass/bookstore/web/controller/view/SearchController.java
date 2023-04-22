package online.javaclass.bookstore.web.controller.view;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SearchController {
    private final BookService bookService;

    @LogInvocation
    @PostMapping("/search")
    public String search(@RequestParam String search, Model model) {
        List<BookDto> searchResult = bookService.search(search);
        model.addAttribute("books", searchResult);
        return "books";
    }
}
