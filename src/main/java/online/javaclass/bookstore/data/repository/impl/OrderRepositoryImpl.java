package online.javaclass.bookstore.data.repository.impl;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.data.entities.Order;
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
    private final EntityManager entityManager;

    @Override
    public Long count() {
        Query query = entityManager.createQuery("SELECT COUNT (*) FROM Order");
        return (Long) query.getSingleResult();
    }

    @Override
    public List<Order> findAllByUserId(Long userId) {
        return entityManager
                .createQuery("FROM Order WHERE user = :user_id ORDER BY id", Order.class)
                .setParameter("user_id", userId)
                .getResultList();
    }

    @Override
    public Order findById(Long id) {
        return entityManager.find(Order.class, id);
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
    public Order create(Order order) {
        entityManager.persist(order);
        return order;
    }

    @Override
    public Order update(Order order) {
        entityManager.detach(order);
        entityManager.merge(order);
        return order;
    }

    @Override
    public boolean deleteById(Long id) {
        boolean deleted = false;
        Order toDelete = entityManager.find(Order.class, id);
        if (toDelete != null) {
            entityManager.remove(toDelete);
            deleted = true;
        }
        return deleted;
    }
}
