package online.javaclass.bookstore.data.dao;

import online.javaclass.bookstore.data.entities.Order;

import java.util.List;

public interface OrderDao extends AbstractDao<Long, Order> {
    Long count();

    List<Order> getAllByUserId(Long userId);
}
