package online.javaclass.bookstore.data.repository;

import online.javaclass.bookstore.data.entities.Book;

import java.util.List;

public interface BookRepository extends AbstractRepository<Long, Book> {

    Book findByIsbn(String isbn);

    List<Book> findByAuthor(String author);
}
