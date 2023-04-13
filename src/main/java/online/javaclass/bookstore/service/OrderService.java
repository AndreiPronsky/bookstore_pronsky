package online.javaclass.bookstore.service;

import online.javaclass.bookstore.service.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService extends AbstractService<Long, OrderDto> {

    Page<OrderDto> getAllByUserId(Pageable pageable, Long userId);

    void validate(OrderDto orderDto);

}
