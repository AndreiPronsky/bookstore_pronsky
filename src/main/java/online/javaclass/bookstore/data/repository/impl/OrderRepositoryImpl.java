package online.javaclass.bookstore.data.repository.impl;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.data.EntityDtoMapperData;
import online.javaclass.bookstore.data.dao.OrderDao;
import online.javaclass.bookstore.data.dao.OrderItemDao;
import online.javaclass.bookstore.data.dao.UserDao;
import online.javaclass.bookstore.data.dto.OrderDto;
import online.javaclass.bookstore.data.dto.OrderItemDto;
import online.javaclass.bookstore.data.dto.UserDto;
import online.javaclass.bookstore.data.entities.Order;
import online.javaclass.bookstore.data.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private final OrderDao orderDao;
    private final OrderItemDao orderItemDao;
    private final UserDao userDao;
    private final EntityDtoMapperData mapper;

    @Override
    public Long count() {
        return orderDao.count();
    }

    @Override
    public List<Order> getAllByUserId(Long userId) {
        List<Order> orders = new ArrayList<>();
        List<OrderDto> orderDtos = orderDao.getAllByUserId(userId);
        UserDto user = userDao.getById(userId);
        for (OrderDto orderDto : orderDtos) {
            List<OrderItemDto> itemDtos = orderItemDao.getAllByOrderId(orderDto.getId());
            orderDto.setItems(itemDtos);
            orderDto.setCost(calculateCost(itemDtos));
            Order order = mapper.toEntity(orderDto);
            order.setUser(mapper.toEntity(user));
            orders.add(order);
        }
        return orders;
    }

    @Override
    public Order getById(Long id) {
        OrderDto orderDto = orderDao.getById(id);
        return buildOrder(orderDto);
    }

    @Override
    public List<Order> getAll(int limit, int offset) {
        List<Order> orders = new ArrayList<>();
        List<OrderDto> orderDtoList = orderDao.getAll(limit, offset);
        for (OrderDto orderDto : orderDtoList) {
            Order order = buildOrder(orderDto);
            order.setUser(mapper.toEntity(userDao.getById(orderDto.getUserId())));
            orders.add(order);
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
        updatedOrder.setUserId(order.getUser().getId());
        return mapper.toEntity(updatedOrder);
    }

    @Override
    public boolean deleteById(Long id) {
        return orderDao.deleteById(id);
    }

    private Order buildOrder(OrderDto orderDto) {
        List<OrderItemDto> orderItemDtos = orderItemDao.getAllByOrderId(orderDto.getId());
        orderDto.setItems(orderItemDtos);
        Order order = mapper.toEntity(orderDto);
        order.setUser(mapper.toEntity(userDao.getById(orderDto.getUserId())));
        return order;
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
