package online.javaclass.bookstore.data.repository;

import online.javaclass.bookstore.data.entities.Order;

import java.util.List;

public interface OrderRepository extends AbstractRepository<Long, Order> {
    @Override
    Order findById(Long id);

    @Override
    List<Order> findAll();

    @Override
    Order create(Order entity);

    @Override
    Order update(Order entity);

    @Override
    boolean deleteById(Long id);
}
