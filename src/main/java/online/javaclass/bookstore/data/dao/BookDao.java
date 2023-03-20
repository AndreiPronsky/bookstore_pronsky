package online.javaclass.bookstore.data.dao;

import online.javaclass.bookstore.data.entities.Book;

import java.util.List;

public interface BookDao extends AbstractDao<Long, Book> {
    Book getByIsbn(String isbn);

    List<Book> getByAuthor(String author, int limit, int offset);

    Long count();

    List<Book> getAll(int limit, int offset);

    List<Book> search(String input);
}
