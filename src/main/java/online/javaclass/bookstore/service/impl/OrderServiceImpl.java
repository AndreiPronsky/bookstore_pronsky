package online.javaclass.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.PagingUtil;
import online.javaclass.bookstore.data.entities.Order;
import online.javaclass.bookstore.data.repository.OrderRepository;
import online.javaclass.bookstore.exceptions.UnableToDeleteException;
import online.javaclass.bookstore.exceptions.UnableToFindException;
import online.javaclass.bookstore.exceptions.ValidationException;
import online.javaclass.bookstore.platform.MessageManager;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.EntityDtoMapperService;
import online.javaclass.bookstore.service.OrderService;
import online.javaclass.bookstore.service.dto.OrderDto;
import online.javaclass.bookstore.service.dto.OrderItemDto;
import online.javaclass.bookstore.service.dto.PageableDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepo;
    private final EntityDtoMapperService mapper;
    private final MessageManager messageManager;

    private final PagingUtil pagingUtil;

    @LogInvocation
    @Override
    public List<OrderDto> getOrdersByUserId(Long userId) {
        List<OrderDto> orders = orderRepo.findAllByUserId(userId).stream()
                .map(mapper::toDto)
                .toList();
        if (orders.isEmpty()) {
            throw new UnableToFindException(messageManager.getMessage("orders.unable_to_find_user_id" + " " + userId));
        } else {
            return orders;
        }
    }

    @Override
    public Long count() {
        return orderRepo.count();
    }

    @LogInvocation
    @Override
    public OrderDto getById(Long id) {
        Order order = orderRepo.findById(id);
        if (order == null) {
            throw new UnableToFindException(messageManager.getMessage("order.unable_to_find_id"));
        }
        return mapper.toDto(order);
    }

    @LogInvocation
    @Override
    public List<OrderDto> getAll(PageableDto pageable) {
        List<OrderDto> orders = orderRepo.findAll(pageable.getLimit(), pageable.getOffset()).stream()
                .map(mapper::toDto)
                .toList();
        if (orders.isEmpty()) {
            throw new UnableToFindException(messageManager.getMessage("orders.unable_to_find"));
        } else {
            Long totalItems = orderRepo.count();
            Long totalPages = pagingUtil.getTotalPages(totalItems, pageable);
            pageable.setTotalItems(orderRepo.count());
            pageable.setTotalPages(totalPages);
            return orders;
        }
    }

    @LogInvocation
    @Override
    public OrderDto create(OrderDto orderDto) throws ValidationException {
        validate(orderDto);
        Order order = mapper.toEntity(orderDto);
        Order created = orderRepo.create(order);
        return mapper.toDto(created);
    }

    @LogInvocation
    @Override
    public OrderDto update(OrderDto orderDto) throws ValidationException {
        validate(orderDto);
        Order order = mapper.toEntity(orderDto);
        Order updated = orderRepo.update(order);
        return mapper.toDto(updated);
    }

    @LogInvocation
    @Override
    public void deleteById(Long id) {
        boolean deleted = orderRepo.deleteById(id);
        if (!deleted) {
            throw new UnableToDeleteException(messageManager.getMessage("order.unable_to delete" + " id:" + id));
        }
    }

    @LogInvocation
    @Override
    public void validate(OrderDto order) throws ValidationException {
        List<String> messages = new ArrayList<>();
        List<OrderItemDto> items = order.getItems();
        BigDecimal totalCost = BigDecimal.ZERO;
        for (OrderItemDto item : items) {
            totalCost = totalCost.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        if (!order.getCost().equals(totalCost)) {
            messages.add(messageManager.getMessage("error.invalid_cost"));
        }
        if (!messages.isEmpty()) {
            throw new ValidationException(messages);
        }
    }
}
