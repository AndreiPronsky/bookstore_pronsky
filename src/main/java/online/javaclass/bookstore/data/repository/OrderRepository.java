package online.javaclass.bookstore.data.repository;

import online.javaclass.bookstore.data.entities.Order;

import java.util.List;

public interface OrderRepository extends AbstractRepository<Long, Order> {
    List<Order> findAllByUserId(Long userId);

}
