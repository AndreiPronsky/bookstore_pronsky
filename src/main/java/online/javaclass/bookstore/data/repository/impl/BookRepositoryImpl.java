package online.javaclass.bookstore.data.repository.impl;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.data.entities.Book;
import online.javaclass.bookstore.data.repository.BookRepository;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Repository
@Transactional
public class BookRepositoryImpl implements BookRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @LogInvocation
    @Override
    public List<Book> search(String input) {
        String reformattedForSearchInput = "%" + input + "%";
        return entityManager
                .createQuery("FROM Book WHERE author LIKE : input OR title LIKE :input", Book.class)
                .setParameter("input", reformattedForSearchInput)
                .getResultList();
    }

    @Override
    public Long count() {
        Query query = entityManager.createQuery("SELECT COUNT(*) FROM Book");
        return (Long) query.getSingleResult();
    }

    @LogInvocation
    @Override
    public Book create(Book book) {
        entityManager.persist(book);
        return book;
    }

    @LogInvocation
    @Override
    public Book update(Book book) {
        entityManager.detach(book);
        entityManager.merge(book);
        return findById(book.getId());
    }

    @LogInvocation
    @Override
    public Book findById(Long id) {
        return entityManager.find(Book.class, id);
    }

    @LogInvocation
    @Override
    public Book findByIsbn(String isbn) {
        return entityManager.find(Book.class, isbn);
    }

    @LogInvocation
    @Override
    public List<Book> findByAuthor(String author, int limit, int offset) {
        return entityManager
                .createQuery("FROM Book WHERE author = :author ORDER BY id", Book.class)
                .setParameter("author", author)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @LogInvocation
    @Override
    public List<Book> findAll(int limit, int offset) {
        return entityManager.createQuery("FROM Book", Book.class)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @LogInvocation
    @Override
    public boolean deleteById(Long id) {
        boolean deleted = false;
        Book toDelete = entityManager.find(Book.class, id);
        if (toDelete != null) {
            entityManager.remove(toDelete);
            deleted = true;
        }
        return deleted;
    }
}
