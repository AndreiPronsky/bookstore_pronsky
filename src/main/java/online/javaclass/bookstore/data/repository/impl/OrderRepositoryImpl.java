package online.javaclass.bookstore.data.repository.impl;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.data.dao.OrderDao;
import online.javaclass.bookstore.data.dao.OrderItemDao;
import online.javaclass.bookstore.data.dao.UserDao;
import online.javaclass.bookstore.data.entities.Order;
import online.javaclass.bookstore.data.entities.OrderItem;
import online.javaclass.bookstore.data.entities.User;
import online.javaclass.bookstore.data.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private final OrderDao orderDao;
    private final OrderItemDao orderItemDao;
    private final UserDao userDao;

    @Override
    public Long count() {
        return orderDao.count();
    }

    @Override
    public List<Order> getAllByUserId(Long userId) {
        List<Order> orderList = orderDao.getAllByUserId(userId);
        User user = userDao.getById(userId);
        for (Order order : orderList) {
            List<OrderItem> items = orderItemDao.getAllByOrderId(order.getId());
            order.setItems(items);
            order.setCost(calculateCost(items));
            order.setUser(user);
        }
        return orderList;
    }

    @Override
    public Order getById(Long id) {
        return buildOrder(orderDao.getById(id));
    }

    @Override
    public List<Order> getAll(int limit, int offset) {
        List<Order> orderList = orderDao.getAll(limit, offset);
        for (Order order: orderList) {
            buildOrder(order);
        }
        return orderList;
    }

    @Override
    public Order create(Order order) {
        Order createdOrder = orderDao.create(order);
        List<OrderItem> itemList = order.getItems();
        for (OrderItem item : itemList) {
            item.setOrderId(createdOrder.getId());
        }
        itemList.forEach(orderItemDao::create);
        createdOrder.setItems(itemList);
        return createdOrder;
    }

    @Override
    public Order update(Order order) {
        Order updatedOrder = orderDao.update(order);
        List<OrderItem> itemList = order.getItems();
        orderItemDao.deleteAllByOrderId(order.getId());
        for (OrderItem item : itemList) {
            orderItemDao.create(item);
        }
        updatedOrder.setItems(itemList);
        updatedOrder.setUser(order.getUser());
        return updatedOrder;
    }

    @Override
    public boolean deleteById(Long id) {
        return orderDao.deleteById(id);
    }

    private Order buildOrder(Order order) {
        List<OrderItem> orderItems = orderItemDao.getAllByOrderId(order.getId());
        order.setItems(orderItems);
        order.setUser(userDao.getById(order.getUser().getId()));
        return order;
    }

    private BigDecimal calculateCost(List<OrderItem> items) {
        BigDecimal totalCost = BigDecimal.ZERO;
        for (OrderItem item : items) {
            BigDecimal itemCost = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalCost = totalCost.add(itemCost);
        }
        return totalCost;
    }

}
