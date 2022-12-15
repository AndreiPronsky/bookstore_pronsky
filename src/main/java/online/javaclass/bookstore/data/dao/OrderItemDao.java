package online.javaclass.bookstore.data.dao;

import online.javaclass.bookstore.data.dto.OrderItemDto;

import java.util.List;

public interface OrderItemDao extends AbstractDao<Long, OrderItemDto> {
    List<OrderItemDto> findAllByOrderId(Long orderId);

    List<OrderItemDto> findAllByOrderId(Long orderId, int limit, int offset);

    void deleteAllByOrderId(Long orderId);

}
