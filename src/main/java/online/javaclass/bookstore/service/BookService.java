package online.javaclass.bookstore.service;

import online.javaclass.bookstore.service.dto.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    BookDto getById(Long id);

    Page<BookDto> getAll(Pageable pageable);

    BookDto save(BookDto entity);

    BookDto getByIsbn(String isbn);

    Page<BookDto> getByAuthor(Pageable pageable, String author);

    List<BookDto> search(String input);

    void deleteById(Long id);

}
