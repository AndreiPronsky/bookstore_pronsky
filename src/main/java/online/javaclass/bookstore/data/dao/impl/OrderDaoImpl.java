package online.javaclass.bookstore.data.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.data.connection.DataBaseManager;
import online.javaclass.bookstore.data.dao.OrderDao;
import online.javaclass.bookstore.data.dto.OrderDto;
import online.javaclass.bookstore.service.exceptions.UnableToCreateException;
import online.javaclass.bookstore.service.exceptions.UnableToDeleteException;
import online.javaclass.bookstore.service.exceptions.UnableToUpdateException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class OrderDaoImpl implements OrderDao {

    private static final String FIND_ORDER_BY_ID = "SELECT o.id, o.user_id, os.name AS order_status, " +
            "pm.name AS payment_method, ps.name AS payment_status, dt.name AS delivery_type, o.cost FROM orders o " +
            "JOIN order_status os on o.status_id = os.id " +
            "JOIN delivery_type dt on dt.id = o.delivery_type_id " +
            "JOIN payment_method pm on o.payment_method_id = pm.id " +
            "JOIN payment_status ps on o.payment_status_id = ps.id WHERE o.id = ?";
    private static final String FIND_ALL_ORDERS = "SELECT o.id, o.user_id, os.name AS order_status, " +
            "pm.name AS payment_method, ps.name AS payment_status, dt.name AS delivery_type, o.cost FROM orders o " +
            "JOIN order_status os on o.status_id = os.id " +
            "JOIN delivery_type dt on dt.id = o.delivery_type_id " +
            "JOIN payment_method pm on o.payment_method_id = pm.id " +
            "JOIN payment_status ps on o.payment_status_id = ps.id ";
    private static final String CREATE_ORDER = "INSERT INTO orders (user_id, status_id, payment_method_id, payment_status_id," +
            "delivery_type_id, cost) VALUES (?, " +
            "(SELECT os.id FROM order_status os WHERE os.name = ?), " +
            "(SELECT pm.id FROM payment_method pm WHERE pm.name = ?), " +
            "(SELECT ps.id FROM payment_status ps WHERE ps.name = ?), " +
            "(SELECT dt.id FROM delivery_type dt WHERE dt.name = ?), ?)";
    private static final String UPDATE_ORDER = "UPDATE orders o SET user_id = ?, status_id = ?, payment_method_id = ?, " +
            "payment_status_id = ?, delivery_type_id = ?, cost = ? WHERE o.id = ?";
    private static final String DELETE_ORDER_BY_ID = "DELETE FROM orders o WHERE o.id = ?";
    private static final String COUNT_ORDERS = "SELECT count(*) FROM orders";
    private static final String COL_STATUS = "order_status";
    private static final String COL_PAYMENT_METHOD = "payment_method";
    private static final String COL_PAYMENT_STATUS = "payment_status";
    private static final String COL_DELIVERY_TYPE = "delivery_type";
    private static final String COL_USER_ID = "user_id";
    private static final String COL_ID = "id";
    private final DataBaseManager dataBaseManager;

    @Override
    public OrderDto findById(Long id) {
        OrderDto order = new OrderDto();
        try (Connection connection = dataBaseManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_ORDER_BY_ID);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                setParameters(order, result);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Long count() {
        try (Connection connection = dataBaseManager.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(COUNT_ORDERS);
            log.debug("DB query completed");
            result.next();
            return result.getLong("count");
        } catch (SQLException e) {
            throw new RuntimeException("Count failed!", e);
        }
    }

    @Override
    public List<OrderDto> findAll() {
        List<OrderDto> orders = new ArrayList<>();
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_ORDERS)) {
            addOrdersToList(orders, statement);
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException("No books found", e);
        }
    }

    @Override
    public OrderDto create(OrderDto order) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatementForCreate(order, statement);
            statement.executeUpdate();
            log.debug("DB query completed");
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                order.setId(result.getLong(COL_ID));
            }
            log.debug("Created order with id" + result.getLong(COL_ID));
            return findById(result.getLong(COL_ID));
        } catch (Exception e) {
            throw new UnableToCreateException("Creation failed! " + order, e);
        }
    }

    @Override
    public OrderDto update(OrderDto order) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER)) {
            prepareStatementForUpdate(order, statement);
            statement.executeUpdate();
            log.debug("DB query completed");
            return findById(order.getId());
        } catch (SQLException e) {
            throw new UnableToUpdateException("Update failed! " + order, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ORDER_BY_ID)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            log.debug("DB query completed");
            return affectedRows == 1;
        } catch (SQLException e) {
            throw new UnableToDeleteException("Unable to delete order with id " + id, e);
        }
    }

    private static void setParameters(OrderDto order, ResultSet result) throws SQLException {
        order.setUserId(result.getLong(COL_USER_ID));
        order.setOrderStatus(OrderDto.OrderStatus.values()[result.getInt(COL_STATUS)]);
        order.setPaymentMethod(OrderDto.PaymentMethod.values()[result.getInt(COL_PAYMENT_METHOD)]);
        order.setPaymentStatus(OrderDto.PaymentStatus.valueOf(result.getString(COL_PAYMENT_STATUS)));
        order.setDeliveryType(OrderDto.DeliveryType.valueOf(result.getString(COL_DELIVERY_TYPE)));
    }

    private void prepareStatementForCreate(OrderDto order, PreparedStatement statement) throws SQLException {
        statement.setLong(1, order.getUserId());
        statement.setInt(2, order.getOrderStatus().ordinal() + 1);
        statement.setInt(3, order.getPaymentMethod().ordinal() + 1);
        statement.setInt(4, order.getPaymentStatus().ordinal() + 1);
        statement.setInt(5, order.getDeliveryType().ordinal() + 1);
        statement.setBigDecimal(6, order.getCost());
    }

    private void prepareStatementForUpdate(OrderDto order, PreparedStatement statement) throws SQLException {
        statement.setLong(1, order.getUserId());
        statement.setInt(2, order.getOrderStatus().ordinal());
        statement.setInt(3, order.getPaymentMethod().ordinal());
        statement.setInt(4, order.getPaymentStatus().ordinal());
        statement.setInt(5, order.getDeliveryType().ordinal());
        statement.setBigDecimal(6, order.getCost());
        statement.setLong(7, order.getId());
    }

    private void addOrdersToList(List<OrderDto> orders, PreparedStatement statement) throws SQLException {
        ResultSet result = statement.executeQuery();
        log.debug("DB query completed");
        while (result.next()) {
            OrderDto order = new OrderDto();
            setParameters(order, result);
            orders.add(order);
        }
    }
}
