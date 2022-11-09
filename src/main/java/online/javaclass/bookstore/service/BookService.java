package online.javaclass.bookstore.service;

import online.javaclass.bookstore.service.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto create(BookDto book);

    BookDto update(BookDto book);

    BookDto getById(Long id);

    BookDto getByIsbn(String isbn);

    List<BookDto> getByAuthor(String author);

    List<BookDto> getAll();

    void deleteById(Long id);
}
