package online.javaclass.bookstore.web.controller.rest;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookRestController {
    private final BookService bookService;

    @GetMapping("/{id}")
    public BookDto get(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @GetMapping
    public Page<BookDto> getAll(Pageable pageable) {
        return bookService.getAll(pageable);
    }

    @PostMapping
    public ResponseEntity<BookDto> create(@RequestBody @Valid BookDto book) {
        BookDto created = bookService.save(book);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(created);
    }

    @PutMapping("/{id}")
    public BookDto update(@PathVariable Long id, @RequestBody @Valid BookDto book) {
        book.setId(id);
        return bookService.save(book);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BookDto updatePart(@PathVariable Long id, @RequestBody @Valid BookDto book) {
        book.setId(id);
        return bookService.save(book);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}
