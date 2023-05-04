package online.javaclass.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.data.entities.Order;
import online.javaclass.bookstore.data.repository.OrderRepository;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.OrderService;
import online.javaclass.bookstore.service.dto.OrderDto;
import online.javaclass.bookstore.service.dto.OrderItemDto;
import online.javaclass.bookstore.service.exceptions.UnableToFindException;
import online.javaclass.bookstore.service.exceptions.ValidationException;
import online.javaclass.bookstore.service.mapper.Mapper;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static online.javaclass.bookstore.service.dto.OrderDto.OrderStatus.*;
import static online.javaclass.bookstore.service.dto.OrderDto.PaymentStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepo;
    private final Mapper mapper;
    private final MessageSource messageSource;


    @LogInvocation
    @Override
    public OrderDto save(OrderDto orderDto) throws ValidationException {
        Order saved;
        if (orderDto.getId() == null) {
            saved = create(orderDto);
        } else {
            saved = update(orderDto);
        }
        return mapper.toDto(saved);
    }

    @LogInvocation
    @Override
    public OrderDto getById(Long id) {
        return orderRepo.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new UnableToFindException(getFailureMessage("order.unable_to_find_id", id)));
    }

    @LogInvocation
    @Override
    public Page<OrderDto> getAllByUserId(Pageable pageable, Long userId) {
        return orderRepo.findAllByUserId(pageable, userId)
                .map(mapper::toDto);
    }

    @LogInvocation
    @Override
    public Page<OrderDto> getAll(Pageable pageable) {
        return orderRepo.findAll(pageable)
                .map(mapper::toDto);
    }

    @org.springframework.transaction.annotation.Transactional(propagation = Propagation.NEVER)
    private Order create(OrderDto orderDto) {
        orderDto.setOrderStatus(OPEN);
        orderDto.setPaymentStatus(OrderDto.PaymentStatus.UNPAID);
        validateCost(orderDto);
        return orderRepo.save(mapper.toEntity(orderDto));
    }

    private Order update(OrderDto orderDto) {
        validateCost(orderDto);
        validateStatusChange(orderDto);
        validatePaymentStatusChange(orderDto);
        return orderRepo.save(mapper.toEntity(orderDto));
    }

    @LogInvocation
    private void validateCost(OrderDto order) {
        List<String> messages = new ArrayList<>();
        List<OrderItemDto> items = order.getItems();
        BigDecimal totalCost = BigDecimal.ZERO;
        for (OrderItemDto item : items) {
            totalCost = totalCost.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        if (!order.getCost().equals(totalCost)) {
            messages.add(getFailureMessage("error.invalid_cost"));
        }
        if (!messages.isEmpty()) {
            throw new ValidationException(messages);
        }
    }

    private boolean isChangeableOrderStatus(OrderDto.OrderStatus currentStatus) {
        return (!(currentStatus.equals(CANCELLED) || currentStatus.equals(COMPLETED)));
    }

    @LogInvocation
    private void validateStatusChange(OrderDto orderToSave) {
        boolean valid = false;
        OrderDto.OrderStatus statusToSave = orderToSave.getOrderStatus();
        OrderDto.OrderStatus currentStatus = orderRepo.findById(orderToSave.getId())
                .map(mapper::toDto)
                .get().getOrderStatus();
        if (isChangeableOrderStatus(currentStatus)) {
            if (currentStatus.equals(statusToSave)) {
                valid = true;
            }
            if (currentStatus.equals(OPEN) && !statusToSave.equals(OPEN)) {
                valid = true;
            }
            if (currentStatus.equals(CONFIRMED) && (statusToSave.equals(COMPLETED) || statusToSave.equals(CANCELLED))) {
                valid = true;
            }
        }
        if (!valid) {
            throw new ValidationException(getFailureMessage("error.invalid_order_status"));
        }
    }

    private boolean isChangeablePaymentStatus(OrderDto.PaymentStatus currentStatus) {
        return (!currentStatus.equals(REFUNDED));
    }

    @LogInvocation
    private void validatePaymentStatusChange(OrderDto orderToSave) {
        boolean valid = false;
        OrderDto.PaymentStatus statusToSave = orderToSave.getPaymentStatus();
        OrderDto.PaymentStatus currentStatus = orderRepo.findById(orderToSave.getId())
                .map(mapper::toDto)
                .get().getPaymentStatus();
        if (isChangeablePaymentStatus(currentStatus)) {
            if (currentStatus.equals(statusToSave)) {
                valid = true;
            }
            if (currentStatus.equals(UNPAID) && (statusToSave.equals(FAILED) || statusToSave.equals(PAID))) {
                valid = true;
            }
            if (currentStatus.equals(FAILED) && statusToSave.equals(PAID)) {
                valid = true;
            }
            if (currentStatus.equals(PAID) && statusToSave.equals(REFUNDED)) {
                orderToSave.setOrderStatus(CANCELLED);
                valid = true;
            }
        }
        if (!valid) {
            throw new ValidationException(getFailureMessage("error.invalid_payment_status"));
        }
    }

    private String getFailureMessage(String key, Object... objects) {
        return messageSource.getMessage(key, objects, LocaleContextHolder.getLocale());
    }
}
