package online.javaclass.bookstore.service;

import online.javaclass.bookstore.service.dto.OrderDto;

import java.util.List;

public interface OrderService extends AbstractService<Long, OrderDto> {

    List<OrderDto> getOrdersByUserId(Long userId);

    void validate(OrderDto orderDto);

}
