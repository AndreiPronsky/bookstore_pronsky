package online.javaclass.bookstore.data.repository.impl;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.data.entities.User;
import online.javaclass.bookstore.data.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long count() {
        Query query = entityManager.createQuery("SELECT COUNT(*) FROM User");
        return (Long) query.getSingleResult();
    }

    @Override
    public User create(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public User update(User user) {
        entityManager.detach(user);
        entityManager.merge(user);
        return user;
    }

    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findByEmail(String email) {
        Query query = entityManager
                .createQuery("FROM User WHERE email = :email")
                .setParameter("email", email);
        return (User) query.getSingleResult();
    }

    @Override
    public List<User> findAll(int limit, int offset) {
        return entityManager
                .createQuery("FROM User", User.class)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public List<User> findByLastName(String lastname, int limit, int offset) {
        return entityManager
                .createQuery("FROM User WHERE lastName = :lastname ORDER BY id", User.class)
                .setParameter("lastname", lastname)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public void deleteById(Long id) {
        User toDelete = entityManager.find(User.class, id);
        entityManager.remove(toDelete);
    }
}
