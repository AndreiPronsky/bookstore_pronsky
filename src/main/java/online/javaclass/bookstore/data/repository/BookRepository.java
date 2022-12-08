package online.javaclass.bookstore.data.repository;

import online.javaclass.bookstore.data.entities.Book;

import java.util.List;

public interface BookRepository extends AbstractRepository<Long, Book> {
    @Override
    Book findById(Long id);

    @Override
    List<Book> findAll();

    @Override
    Book create(Book entity);

    @Override
    Book update(Book entity);

    @Override
    boolean deleteById(Long id);

    Book findByIsbn(String isbn);

    List<Book> findByAuthor(String author);
}
