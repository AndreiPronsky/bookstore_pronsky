package online.javaclass.bookstore.data.dao;

import online.javaclass.bookstore.data.dto.OrderItemDto;

import java.util.List;

public interface OrderItemDao extends AbstractDao<Long, OrderItemDto> {
    List<OrderItemDto> getAllByOrderId(Long orderId);

    void deleteAllByOrderId(Long orderId);
}
