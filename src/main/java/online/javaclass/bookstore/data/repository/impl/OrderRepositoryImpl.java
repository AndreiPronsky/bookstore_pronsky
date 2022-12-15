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
import online.javaclass.bookstore.data.entities.OrderItem;
import online.javaclass.bookstore.data.entities.User;
import online.javaclass.bookstore.data.repository.OrderRepository;

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
    public Order findById(Long id) {
        OrderDto orderDto = orderDao.findById(id);
        return buildOrder(orderDto);
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        List<OrderDto> orderDtoList = orderDao.findAll();
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
        orderItemDao.deleteAllByOrderId(orderDto.getId());
        List<OrderItemDto> itemDtos = orderDto.getItems();
        for (OrderItemDto item : itemDtos) {
            orderItemDao.create(item);
        }
        return mapper.toEntity(updatedOrder);
    }

    @Override
    public boolean deleteById(Long id) {
        return orderDao.deleteById(id);
    }

    private Order buildOrder(OrderDto orderDto) {
        Order order = mapper.toEntity(orderDto);
        UserDto userDto = userDao.findById(orderDto.getUserId());
        User user = mapper.toEntity(userDto);
        order.setUser(user);
        List<OrderItemDto> orderItemDtos = orderItemDao.findAllByOrderId(order.getId());
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDto itemDto : orderItemDtos) {
            OrderItem item = mapper.toEntity(itemDto);
            item.setBookId(itemDto.getBookId());
            orderItems.add(item);
        }
        order.setItems(orderItems);
        return order;
    }
}
