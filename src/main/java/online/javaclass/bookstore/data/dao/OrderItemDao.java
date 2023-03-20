package online.javaclass.bookstore.data.dao;

import online.javaclass.bookstore.data.entities.OrderItem;

import java.util.List;

public interface OrderItemDao extends AbstractDao<Long, OrderItem> {
    List<OrderItem> getAllByOrderId(Long orderId);

    void deleteAllByOrderId(Long orderId);
}
