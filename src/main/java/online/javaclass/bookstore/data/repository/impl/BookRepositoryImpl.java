package online.javaclass.bookstore.data.repository.impl;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.data.dao.BookDao;
import online.javaclass.bookstore.data.entities.Book;
import online.javaclass.bookstore.data.repository.BookRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BookRepositoryImpl implements BookRepository {
    private final BookDao bookDao;

    /**
     * Takes input parameter and invokes getById method of a DAO layer.
     *
     * @param id Long value of book id to find existing book.
     * @return Object returned from DAO layer mapped to an entity object.
     */
    @Override
    public Book getById(Long id) {
        return bookDao.getById(id);
    }

    /**
     * Takes input parameter and invokes getByIsbn method of a DAO layer.
     *
     * @param isbn String value of book id to find existing book.
     * @return Object returned from DAO layer mapped to an entity object.
     */
    @Override
    public Book getByIsbn(String isbn) {
        return bookDao.getByIsbn(isbn);
    }

    /**
     * Takes input parameter and invokes search method of a DAO layer.
     *
     * @param input String value of complete or partial value of a book title or author.
     * @return List of objects returned from DAO layer mapped to an entity objects.
     */
    @Override
    public List<Book> search(String input) {
        return bookDao.search(input);
    }

    /**
     * Takes input parameter and invokes getByAuthor method of a DAO layer.
     *
     * @param author String value of complete value of a book author.
     * @param limit  Integer value of number of entities to be extracted from database.
     * @param offset Integer value of number of first entities in a list to be skipped
     * @return List of objects returned from DAO layer mapped to an entity objects.
     */
    @Override
    public List<Book> getByAuthor(String author, int limit, int offset) {
        return bookDao.getByAuthor(author, limit, offset);
    }

    /**
     * Invokes getAll method of a DAO layer.
     *
     * @param limit  Integer value of number of entities to be extracted from database.
     * @param offset Integer value of number of first entities in a list to be skipped
     * @return List of objects returned from DAO layer mapped to an entity objects.
     */
    @Override
    public List<Book> getAll(int limit, int offset) {
        return bookDao.getAll(limit, offset);
    }

    /**
     * Takes input parameters from entity object, maps it to data transfer object and invokes a create method
     * of a DAO layer.
     *
     * @param book is an entity object to be created.
     * @return Object returned from DAO layer mapped to an entity object.
     */
    @Override
    public Book create(Book book) {
        return bookDao.create(book);
    }

    /**
     * Takes input parameters from entity object, maps it to data transfer object and invokes an update method
     * of a DAO layer.
     *
     * @param book is an entity object to be updated.
     * @return Object returned from DAO layer mapped to an entity object.
     */
    @Override
    public Book update(Book book) {
        return bookDao.update(book);
    }

    /**
     * Takes input parameter and invokes deleteById method of a DAO layer.
     *
     * @param id Long value of book id to delete existing book.
     * @return boolean value got from DAO layer indicating if book was deleted.
     */
    @Override
    public boolean deleteById(Long id) {
        return bookDao.deleteById(id);
    }

    /**
     * Invokes count method of a DAO layer.
     *
     * @return Long value of existing items in the database got from DAO layer
     */
    @Override
    public Long count() {
        return bookDao.count();
    }
}
