package online.javaclass.bookstore.data.dao;

import online.javaclass.bookstore.data.entities.Book;

import java.util.List;

public interface BookDao {
    void create(Book book);

    void update(Book book);

    Book findBookById(Long id);

    Book findBookByIsbn(String isbn);

    List<Book> findBooksByAuthor(String author);
    List<Book> findAll();

    boolean deleteById(Long id);
}
