package online.javaclass.bookstore.data.dao;

import online.javaclass.bookstore.data.dto.BookDto;

import java.util.List;

public interface BookDao extends AbstractDao<Long, BookDto> {
    BookDto getByIsbn(String isbn);

    List<BookDto> getByAuthor(String author, int limit, int offset);

    Long count();

    List<BookDto> getAll(int limit, int offset);

    List<BookDto> search(String input);
}
