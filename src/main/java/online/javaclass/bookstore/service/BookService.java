package online.javaclass.bookstore.service;

import online.javaclass.bookstore.service.dto.PageableDto;
import online.javaclass.bookstore.service.dto.BookDto;

import java.util.List;

public interface BookService extends AbstractService<Long, BookDto> {

    BookDto getByIsbn(String isbn);

    List<BookDto> getByAuthor(String author);

    List<BookDto> getByAuthor(String author, PageableDto pageable);

    List<BookDto> search(String input);

}
