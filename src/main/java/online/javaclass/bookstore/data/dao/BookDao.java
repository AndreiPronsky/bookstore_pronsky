package online.javaclass.bookstore.data.dao;

import online.javaclass.bookstore.data.entities.Book;

import java.util.List;

public interface BookDao {
    Book create(Book book);

    Book update(Book book);

    Book findById(Long id);

    Book findByIsbn(String isbn);

    List<Book> findByAuthor(String author);

    List<Book> findAll();

    boolean deleteById(Long id);
}
