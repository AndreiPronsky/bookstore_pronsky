package online.javaclass.bookstore.data.dao;

import online.javaclass.bookstore.data.dto.BookDto;

import java.util.List;

public interface BookDao extends AbstractDao<Long, BookDto> {
    BookDto findByIsbn(String isbn);

    List<BookDto> findByAuthor(String author);
}
