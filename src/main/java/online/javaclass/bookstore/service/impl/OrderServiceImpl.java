package online.javaclass.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.data.entities.Order;
import online.javaclass.bookstore.data.repository.OrderRepository;
import online.javaclass.bookstore.platform.MessageManager;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.EntityDtoMapper;
import online.javaclass.bookstore.service.OrderService;
import online.javaclass.bookstore.service.dto.OrderDto;
import online.javaclass.bookstore.service.dto.OrderItemDto;
import online.javaclass.bookstore.service.exceptions.UnableToFindException;
import online.javaclass.bookstore.service.exceptions.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepo;
    private final EntityDtoMapper mapper;
    private final MessageManager messageManager;

    @Override
    public Long count() {
        return orderRepo.count();
    }

    @LogInvocation
    @Override
    public OrderDto save(OrderDto orderDto) throws ValidationException {
        if (orderDto.getOrderStatus() == null || orderDto.getPaymentStatus() == null) {
            orderDto.setOrderStatus(OrderDto.OrderStatus.OPEN);
            orderDto.setPaymentStatus(OrderDto.PaymentStatus.UNPAID);
        }
        validate(orderDto);
        Order order = mapper.toEntity(orderDto);
        Order created = orderRepo.save(order);
        return mapper.toDto(created);
    }

    @LogInvocation
    @Override
    public OrderDto getById(Long id) {
        return mapper.toDto(orderRepo.findById(id)
                .orElseThrow(() -> new UnableToFindException("order.unable_to_find_id" + " " + id)));
    }

    @LogInvocation
    @Override
    public List<OrderDto> getOrdersByUserId(Long userId) {
        List<OrderDto> orders = orderRepo.findAllByUserId(userId).stream()
                .map(mapper::toDto)
                .toList();
        if (orders.isEmpty()) {
            throw new UnableToFindException(messageManager.getMessage("orders.unable_to_find_user_id" + " " + userId));
        }
        return orders;
    }

    @LogInvocation
    @Override
    public Page<OrderDto> getAll(Pageable pageable) {
        Page<OrderDto> orders = orderRepo.findAll(pageable).map(mapper::toDto);
        if (orders.isEmpty()) {
            throw new UnableToFindException(messageManager.getMessage("orders.unable_to_find"));
        }
        return orders;
    }

    @LogInvocation
    @Override
    public void deleteById(Long id) {
        orderRepo.deleteById(id);
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
