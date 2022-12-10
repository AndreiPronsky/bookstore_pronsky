package online.javaclass.bookstore.data.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.data.connection.DataBaseManager;
import online.javaclass.bookstore.data.dao.OrderItemDao;
import online.javaclass.bookstore.data.dto.OrderItemDto;
import online.javaclass.bookstore.service.exceptions.UnableToCreateException;
import online.javaclass.bookstore.service.exceptions.UnableToDeleteException;
import online.javaclass.bookstore.service.exceptions.UnableToFindException;
import online.javaclass.bookstore.service.exceptions.UnableToUpdateException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Log4j2
public class OrderItemDaoImpl implements OrderItemDao {
    private static final String FIND_ITEMS_BY_ORDER_ID = "SELECT item_id, order_id, book_id, quantity, price FROM order_items" +
            " WHERE order_id = ?";
    private static final String FIND_ITEM_BY_ID = "SELECT item_id, order_id, book_id, quantity, price FROM order_items " +
            "WHERE item_id = ?";
    private static final String FIND_ALL = "SELECT item_id, order_id, book_id, quantity, price FROM order_items";
    private static final String CREATE_ITEM = "INSERT INTO order_items (order_id, book_id, quantity, price) " +
            "VALUES (?, ?, ?, ?)";
    private static final String UPDATE_ITEM = "UPDATE order_items SET order_id = ?, book_id = ?, quantity = ?, " +
            "price = ? WHERE item_id = ?";
    private static final String DELETE_ITEM_BY_ID = "DELETE FROM order_items WHERE item_id = ?";

    private static final String COL_ID = "item_id";
    private static final String COL_ORDER_ID = "order_id";
    private static final String COL_BOOK_ID = "book_id";
    private static final String COL_QUANTITY = "quantity";
    private static final String COL_PRICE = "price";

    private final DataBaseManager dataBaseManager;

    @Override
    public List<OrderItemDto> findAllByOrderId(Long orderId) {
        List<OrderItemDto> orderItems = new ArrayList<>();
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ITEMS_BY_ORDER_ID)) {
            statement.setLong(1, orderId);
            addItemsToList(orderItems, statement);
            return orderItems;
        } catch (SQLException e) {
            throw new RuntimeException("No order items with order id " + orderId + " found", e);
        }
    }

    @Override
    public void deleteAllByOrderId(Long orderId) {
        for (OrderItemDto item : findAllByOrderId(orderId)) {
            deleteById(item.getId());
        }
    }

    @Override
    public OrderItemDto findById(Long id) {
        OrderItemDto item = new OrderItemDto();
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ITEM_BY_ID)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                setParameters(item, result);
            }
            log.debug("DB query completed");
            return item;
        } catch (SQLException e) {
            throw new UnableToFindException("No such item found! " + item, e);
        }
    }

    @Override
    public List<OrderItemDto> findAll() {
        List<OrderItemDto> orderItems = new ArrayList<>();
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
            addItemsToList(orderItems, statement);
            return orderItems;
        } catch (SQLException e) {
            throw new RuntimeException("No items found", e);
        }
    }

    @Override
    public OrderItemDto create(OrderItemDto item) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_ITEM, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatementForCreate(item, statement);
            statement.executeUpdate();
            log.debug("DB query completed");
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                item.setId(result.getLong(COL_ID));
            }
            log.debug("Created item with id" + result.getLong(COL_ID));
            return findById(result.getLong(COL_ID));
        } catch (Exception e) {
            throw new UnableToCreateException("Creation failed! " + item, e);
        }
    }

    @Override
    public OrderItemDto update(OrderItemDto item) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ITEM)) {
            prepareStatementForUpdate(item, statement);
            statement.executeUpdate();
            log.debug("DB query completed");
            return findById(item.getId());
        } catch (SQLException e) {
            throw new UnableToUpdateException("Update failed! " + item, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ITEM_BY_ID)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            log.debug("DB query completed");
            return affectedRows == 1;
        } catch (SQLException e) {
            throw new UnableToDeleteException("Unable to delete item with id " + id, e);
        }
    }

    private void addItemsToList(List<OrderItemDto> orderItems, PreparedStatement statement) throws SQLException {
        ResultSet result = statement.executeQuery();
        log.debug("DB query completed");
        while (result.next()) {
            OrderItemDto itemDto = new OrderItemDto();
            setParameters(itemDto, result);
            orderItems.add(itemDto);
        }
    }

    private void setParameters(OrderItemDto item, ResultSet result) throws SQLException {
        item.setId(result.getLong(COL_ID));
        item.setOrderId(result.getLong(COL_ORDER_ID));
        item.setBookId(result.getLong(COL_BOOK_ID));
        item.setQuantity(result.getInt(COL_QUANTITY));
        item.setPrice(result.getBigDecimal(COL_PRICE));
    }

    private void prepareStatementForCreate(OrderItemDto item, PreparedStatement statement) throws SQLException {
        statement.setLong(1, item.getOrderId());
        statement.setLong(2, item.getBookId());
        statement.setInt(3, item.getQuantity());
        statement.setBigDecimal(4, item.getPrice());
    }

    private void prepareStatementForUpdate(OrderItemDto item, PreparedStatement statement) throws SQLException {
        statement.setLong(1, item.getOrderId());
        statement.setLong(2, item.getBookId());
        statement.setInt(3, item.getQuantity());
        statement.setBigDecimal(4, item.getPrice());
        statement.setLong(5, item.getId());
    }
}
