package online.javaclass.bookstore.data.dao.impl;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.LogInvocation;
import online.javaclass.bookstore.MessageManager;
import online.javaclass.bookstore.data.dao.BookDao;
import online.javaclass.bookstore.data.dto.BookDto;
import online.javaclass.bookstore.exceptions.AppException;
import online.javaclass.bookstore.exceptions.UnableToCreateException;
import online.javaclass.bookstore.exceptions.UnableToFindException;
import online.javaclass.bookstore.exceptions.UnableToUpdateException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class BookDaoImpl implements BookDao {

    private final MessageManager messageManager;
    private final EntityManager entityManager;

    @LogInvocation
    @Override
    public List<BookDto> search(String input) {
        try {
            String reformattedForSearchInput = "%" + input + "%";
            return entityManager
                    .createQuery("from BookDto where author like : input or title like :input", BookDto.class)
                    .setParameter("input", reformattedForSearchInput)
                    .getResultList();
        } catch (DataAccessException e) {
            throw new UnableToFindException(messageManager.getMessage("books.unable_to_find_containing")
                    + " " + input + messageManager.getMessage("in_title"));
        }
    }

    @Override
    public Long count() {
        try {
            Query query = entityManager.createQuery("SELECT COUNT(bD) FROM BookDto bD ");
            return (Long)query.getSingleResult();
        } catch (DataAccessException e) {
            throw new AppException(messageManager.getMessage("count_failed"));
        }
    }

    @LogInvocation
    @Override
    public BookDto create(BookDto book) {
        try {
            entityManager.persist(book);
            return book;
        } catch (DataAccessException e) {
            throw new UnableToCreateException(messageManager.getMessage("book.unable_to_create"));
        }
    }

    @LogInvocation
    @Override
    public BookDto update(BookDto book) {
        try {
            entityManager.detach(book);
            entityManager.merge(book);
            return getById(book.getId());
        } catch (DataAccessException e) {
            throw new UnableToUpdateException(messageManager.getMessage("book.unable_to_update"));
        }
    }

    @LogInvocation
    @Override
    public BookDto getById(Long id) {
        try {
            return entityManager.find(BookDto.class, id);
        } catch (DataAccessException e) {
            throw new UnableToFindException(messageManager.getMessage("book.unable_to_find_id") + " " + id);
        }
    }

    @LogInvocation
    @Override
    public BookDto getByIsbn(String isbn) {
        try {
            return entityManager.find(BookDto.class, isbn);
        } catch (DataAccessException e) {
            throw new UnableToFindException(messageManager.getMessage("book.unable_to_find_isbn"));
        }
    }

    @LogInvocation
    @Override
    public List<BookDto> getByAuthor(String author, int limit, int offset) {
        try {
            return entityManager
                    .createQuery("from BookDto where author = :author order by id", BookDto.class)
                    .setParameter("author", author)
                    .setMaxResults(limit)
                    .getResultList();
        } catch (DataAccessException e) {
            throw new UnableToFindException(messageManager.getMessage("books.unable_to_find_author"));
        }
    }

    @LogInvocation
    @Override
    public List<BookDto> getAll(int limit, int offset) {
        try {
            return entityManager.createQuery("from BookDto", BookDto.class).getResultList();
        } catch (DataAccessException e) {
            throw new UnableToFindException(messageManager.getMessage("books.unable_to_find"));
        }
    }

    @LogInvocation
    @Override
    public boolean deleteById(Long id) {
        boolean deleted = false;
        entityManager.getTransaction().begin();
        BookDto toDelete = entityManager.find(BookDto.class, id);
        if (toDelete != null) {
            entityManager.remove(toDelete);
            deleted = true;
        }
        entityManager.getTransaction().commit();
        return deleted;
    }
}