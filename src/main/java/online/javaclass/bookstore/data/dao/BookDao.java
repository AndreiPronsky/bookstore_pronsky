package online.javaclass.bookstore.data.dao;

import online.javaclass.bookstore.data.dto.BookDto;

import java.util.List;

public interface BookDao extends AbstractDao<Long, BookDto> {
    BookDto findByIsbn(String isbn);

    List<BookDto> findByAuthor(String author);

    List<BookDto> findByAuthor(String author, int limit, int offset);

    Long count();

    List<BookDto> findAll(int limit, int offset);

    List<BookDto> search(String input);
}
