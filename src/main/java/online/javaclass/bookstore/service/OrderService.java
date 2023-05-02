package online.javaclass.bookstore.service;

import online.javaclass.bookstore.service.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderDto getById(Long id);

    Page<OrderDto> getAll(Pageable pageable);

    OrderDto save(OrderDto entity);

    Page<OrderDto> getAllByUserId(Pageable pageable, Long userId);

}
