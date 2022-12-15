package online.javaclass.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.service.dto.OrderDto;
import online.javaclass.bookstore.data.entities.Order;
import online.javaclass.bookstore.data.repository.OrderRepository;
import online.javaclass.bookstore.service.EntityDtoMapperService;
import online.javaclass.bookstore.service.OrderService;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepo;
    private final EntityDtoMapperService mapper;

    @Override
    public Long count() {
        return orderRepo.count();
    }

    @Override
    public OrderDto getById(Long id) {
        log.debug("get order by id");
        Order order = orderRepo.findById(id);
        if (order == null) {
            throw new RuntimeException("Book with id " + id + " not found!");
        }
        return mapper.toDto(order);
    }

    @Override
    public List<OrderDto> getAll() {
        log.debug("get all orders");
        return orderRepo.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public OrderDto create(OrderDto orderDto) {
        log.debug("create order");
        Order order = mapper.toEntity(orderDto);
        Order created = orderRepo.create(order);
        return mapper.toDto(created);
    }

    @Override
    public OrderDto update(OrderDto orderDto) {
        log.debug("update order");
        Order order = mapper.toEntity(orderDto);
        Order updated = orderRepo.update(order);
        return mapper.toDto(updated);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("delete order by id");
        boolean deleted = orderRepo.deleteById(id);
        if (!deleted) {
            log.error("Unable to delete order with id : " + id);
        }
    }
}
