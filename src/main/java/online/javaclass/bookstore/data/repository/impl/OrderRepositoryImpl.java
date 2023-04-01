package online.javaclass.bookstore.data.repository.impl;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.data.entities.Order;
import online.javaclass.bookstore.data.entities.User;
import online.javaclass.bookstore.data.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Repository
@Transactional
public class OrderRepositoryImpl implements OrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long count() {
        Query query = entityManager.createQuery("SELECT COUNT (*) FROM Order");
        return (Long) query.getSingleResult();
    }

    @Override
    public Order create(Order order) {
        entityManager.persist(order);
        return order;
    }

    @Override
    public Order update(Order order) {
        entityManager.merge(order);
        return order;
    }

    @Override
    public Order findById(Long id) {
        return entityManager.find(Order.class, id);
    }

    @Override
    public List<Order> findAllByUserId(Long userId) {
        User user = entityManager.find(User.class, userId);
        return entityManager
                .createQuery("FROM Order WHERE user = :user ORDER BY id", Order.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public List<Order> findAll(int limit, int offset) {
        return entityManager
                .createQuery("FROM Order", Order.class)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public void deleteById(Long id) {
        Order toDelete = entityManager.find(Order.class, id);
        entityManager.remove(toDelete);
    }
}
