package online.javaclass.bookstore.data.dao;

import online.javaclass.bookstore.data.dto.OrderDto;

import java.util.List;

public interface OrderDao extends AbstractDao<Long, OrderDto> {
    Long count();

    List<OrderDto> getAllByUserId(Long userId);
}
