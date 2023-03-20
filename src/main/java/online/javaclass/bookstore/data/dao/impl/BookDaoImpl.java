package online.javaclass.bookstore.data.dao.impl;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.platform.MessageManager;
import online.javaclass.bookstore.data.dao.BookDao;
import online.javaclass.bookstore.data.entities.Book;
import online.javaclass.bookstore.exceptions.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class BookDaoImpl implements BookDao {

    private final MessageManager messageManager;
    private final EntityManager entityManager;

    @LogInvocation
    @Override
    public List<Book> search(String input) {
        try {
            String reformattedForSearchInput = "%" + input + "%";
            return entityManager
                    .createQuery("from Book where author like : input or title like :input", Book.class)
                    .setParameter("input", reformattedForSearchInput)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new UnableToFindException(messageManager.getMessage("books.unable_to_find_containing")
                    + " " + input + messageManager.getMessage("in_title"), e);
        }
    }

    @Override
    public Long count() {
        try {
            Query query = entityManager.createQuery("SELECT COUNT(*) FROM Book");
            return (Long)query.getSingleResult();
        } catch (PersistenceException e) {
            throw new AppException(messageManager.getMessage("count_failed"), e);
        }
    }

    @LogInvocation
    @Override
    public Book create(Book book) {
        try {
            entityManager.persist(book);
            return book;
        } catch (PersistenceException e) {
            throw new UnableToCreateException(messageManager.getMessage("book.unable_to_create"), e);
        }
    }

    @LogInvocation
    @Override
    public Book update(Book book) {
        try {
            entityManager.detach(book);
            entityManager.merge(book);
            return getById(book.getId());
        } catch (PersistenceException e) {
            throw new UnableToUpdateException(messageManager.getMessage("book.unable_to_update"), e);
        }
    }

    @LogInvocation
    @Override
    public Book getById(Long id) {
        try {
            return entityManager.find(Book.class, id);
        } catch (PersistenceException e) {
            throw new UnableToFindException(messageManager.getMessage("book.unable_to_find_id") + " " + id, e);
        }
    }

    @LogInvocation
    @Override
    public Book getByIsbn(String isbn) {
        try {
            return entityManager.find(Book.class, isbn);
        } catch (PersistenceException e) {
            throw new UnableToFindException(messageManager.getMessage("book.unable_to_find_isbn"), e);
        }
    }

    @LogInvocation
    @Override
    public List<Book> getByAuthor(String author, int limit, int offset) {
        try {
            return entityManager
                    .createQuery("from Book where author = :author order by id", Book.class)
                    .setParameter("author", author)
                    .setMaxResults(limit)
                    .setFirstResult(offset)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new UnableToFindException(messageManager.getMessage("books.unable_to_find_author"), e);
        }
    }

    @LogInvocation
    @Override
    public List<Book> getAll(int limit, int offset) {
        try {
            return entityManager.createQuery("from Book", Book.class)
                    .setMaxResults(limit)
                    .setFirstResult(offset)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new RuntimeException(e);
            //throw new UnableToFindException(messageManager.getMessage("books.unable_to_find"), e);
        }
    }

    @LogInvocation
    @Override
    public boolean deleteById(Long id) {
        try {
            boolean deleted = false;
            entityManager.getTransaction().begin();
            Book toDelete = entityManager.find(Book.class, id);
            if (toDelete != null) {
                entityManager.remove(toDelete);
                deleted = true;
            }
            entityManager.getTransaction().commit();
            return deleted;
        } catch (PersistenceException e) {
            throw new UnableToDeleteException(messageManager.getMessage("book.unable_to_delete_id"), e);
        }
    }
}