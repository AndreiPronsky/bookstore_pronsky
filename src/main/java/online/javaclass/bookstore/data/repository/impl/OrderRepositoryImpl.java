package online.javaclass.bookstore.data.repository.impl;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.data.EntityDtoMapperData;
import online.javaclass.bookstore.data.dao.OrderDao;
import online.javaclass.bookstore.data.dao.OrderItemDao;
import online.javaclass.bookstore.data.dao.UserDao;
import online.javaclass.bookstore.data.dto.OrderDto;
import online.javaclass.bookstore.data.dto.OrderItemDto;
import online.javaclass.bookstore.data.entities.Order;
import online.javaclass.bookstore.data.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private final OrderDao orderDao;
    private final UserDao userDao;
    private final OrderItemDao orderItemDao;
    private final EntityDtoMapperData mapper;

    @Override
    public Long count() {
        return orderDao.count();
    }

    @Override
    public List<Order> getAllByUserId(Long userId) {
        List<Order> orders = new ArrayList<>();
        List<OrderDto> orderDtos = orderDao.getAllByUserId(userId);
        for (OrderDto orderDto : orderDtos) {
            List<OrderItemDto> itemDtos = orderItemDao.getAllByOrderId(orderDto.getId());
            orderDto.setItems(itemDtos);
            orderDto.setCost(calculateCost(itemDtos));
            orders.add(mapper.toEntity(orderDto));
        }
        return orders;
    }

    @Override
    public Order getById(Long id) {
        OrderDto orderDto = orderDao.getById(id);
        Order order = buildOrder(orderDto);
        return order;
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        List<OrderDto> orderDtoList = orderDao.getAll();
        for (OrderDto orderDto : orderDtoList) {
            orders.add(buildOrder(orderDto));
        }
        return orders;
    }

    @Override
    public List<Order> getAll(int limit, int offset) {
        List<Order> orders = new ArrayList<>();
        List<OrderDto> orderDtoList = orderDao.getAll(limit, offset);
        for (OrderDto orderDto : orderDtoList) {
            orders.add(buildOrder(orderDto));
        }
        return orders;
    }

    @Override
    public Order create(Order order) {
        OrderDto orderDto = mapper.toDto(order);
        OrderDto createdOrder = orderDao.create(orderDto);
        List<OrderItemDto> items = orderDto.getItems();
        for (OrderItemDto item : items) {
            item.setOrderId(createdOrder.getId());
        }
        items.forEach(orderItemDao::create);
        createdOrder.setItems(items);
        return mapper.toEntity(createdOrder);
    }

    @Override
    public Order update(Order order) {
        OrderDto orderDto = mapper.toDto(order);
        OrderDto updatedOrder = orderDao.update(orderDto);
        List<OrderItemDto> itemDtos = orderDto.getItems();
        orderItemDao.deleteAllByOrderId(order.getId());
        for (OrderItemDto item : itemDtos) {
            orderItemDao.create(item);
        }
        updatedOrder.setItems(itemDtos);
        return mapper.toEntity(updatedOrder);
    }

    @Override
    public boolean deleteById(Long id) {
        return orderDao.deleteById(id);
    }

    private Order buildOrder(OrderDto orderDto) {
        List<OrderItemDto> orderItemDtos = orderItemDao.getAllByOrderId(orderDto.getId());
        orderDto.setItems(orderItemDtos);
        return mapper.toEntity(orderDto);
    }

    private BigDecimal calculateCost(List<OrderItemDto> items) {
        BigDecimal totalCost = BigDecimal.ZERO;
        for (OrderItemDto item : items) {
            BigDecimal itemCost = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalCost = totalCost.add(itemCost);
        }
        return totalCost;
    }

}
