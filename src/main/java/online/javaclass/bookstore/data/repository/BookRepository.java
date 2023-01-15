package online.javaclass.bookstore.data.repository;

import online.javaclass.bookstore.data.entities.Book;

import java.util.List;

public interface BookRepository extends AbstractRepository<Long, Book> {

    Book getByIsbn(String isbn);

    List<Book> getByAuthor(String author, int limit, int offset);

    List<Book> search(String input);

}
