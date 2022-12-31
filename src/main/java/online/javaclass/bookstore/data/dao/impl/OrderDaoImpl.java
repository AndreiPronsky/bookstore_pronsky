package online.javaclass.bookstore.data.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.MessageManager;
import online.javaclass.bookstore.data.connection.DataBaseManager;
import online.javaclass.bookstore.data.dao.OrderDao;
import online.javaclass.bookstore.data.dto.OrderDto;
import online.javaclass.bookstore.service.exceptions.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class OrderDaoImpl implements OrderDao {
    private static final String FIND_ORDER_BY_ID = "SELECT o.id, o.user_id, os.name AS status, " +
            "pm.name AS payment_method, ps.name AS payment_status, dt.name AS delivery_type, cost FROM orders o " +
            "JOIN order_status os ON o.status_id = os.id " +
            "JOIN delivery_type dt on dt.id = o.delivery_type_id " +
            "JOIN payment_method pm on o.payment_method_id = pm.id " +
            "JOIN payment_status ps on o.payment_status_id = ps.id " +
            "WHERE o.id = ?";

    private static final String FIND_ORDERS_BY_USER_ID = "SELECT o.id, o.user_id, os.name AS status, " +
            "pm.name AS payment_method, ps.name AS payment_status, dt.name AS delivery_type, cost FROM orders o " +
            "JOIN order_status os ON o.status_id = os.id " +
            "JOIN delivery_type dt on dt.id = o.delivery_type_id " +
            "JOIN payment_method pm on o.payment_method_id = pm.id " +
            "JOIN payment_status ps on o.payment_status_id = ps.id " +
            "WHERE o.user_id = ? ORDER BY o.id";
    private static final String FIND_ALL_ORDERS = "SELECT o.id, o.user_id, os.name AS status, " +
            "pm.name AS payment_method, ps.name AS payment_status, dt.name AS delivery_type, cost FROM orders o " +
            "JOIN order_status os ON o.status_id = os.id " +
            "JOIN delivery_type dt on dt.id = o.delivery_type_id " +
            "JOIN payment_method pm on o.payment_method_id = pm.id " +
            "JOIN payment_status ps on o.payment_status_id = ps.id ";

    private static final String FIND_ALL_ORDERS_PAGED = "SELECT o.id, o.user_id, os.name AS status, " +
            "pm.name AS payment_method, ps.name AS payment_status, dt.name AS delivery_type, cost FROM orders o " +
            "JOIN order_status os ON o.status_id = os.id " +
            "JOIN delivery_type dt on dt.id = o.delivery_type_id " +
            "JOIN payment_method pm on o.payment_method_id = pm.id " +
            "JOIN payment_status ps on o.payment_status_id = ps.id " +
            "LIMIT ? OFFSET ?";
    private static final String CREATE_ORDER = "INSERT INTO orders (user_id, status_id, payment_method_id, payment_status_id," +
            "delivery_type_id, cost) VALUES (?, " +
            "(SELECT os.id FROM order_status os WHERE os.name = ?), " +
            "(SELECT pm.id FROM payment_method pm WHERE pm.name = ?), " +
            "(SELECT ps.id FROM payment_status ps WHERE ps.name = ?), " +
            "(SELECT dt.id FROM delivery_type dt WHERE dt.name = ?), ?)";
    private static final String UPDATE_ORDER = "UPDATE orders o SET user_id = ?, " +
            "status_id = (SELECT os.id FROM order_status os WHERE os.name = ?), " +
            "payment_method_id = (SELECT pm.id FROM payment_method pm WHERE pm.name = ?), " +
            "payment_status_id = (SELECT ps.id FROM payment_status ps WHERE ps.name = ?), " +
            "delivery_type_id = (SELECT dt.id FROM delivery_type dt WHERE dt.name = ?), cost = ? WHERE o.id = ?";
    private static final String DELETE_ORDER_BY_ID = "DELETE FROM orders WHERE id = ?";
    private static final String COUNT_ORDERS = "SELECT count(*) FROM orders";
    private static final String COL_STATUS = "status";
    private static final String COL_PAYMENT_METHOD = "payment_method";
    private static final String COL_PAYMENT_STATUS = "payment_status";
    private static final String COL_DELIVERY_TYPE = "delivery_type";
    private static final String COL_USER_ID = "user_id";
    private static final String COL_ID = "id";
    private static final String COL_COST = "cost";
    private final DataBaseManager dataBaseManager;

    private final ThreadLocal<MessageManager> context = new ThreadLocal<>();
    MessageManager messageManager = context.get();

    @Override
    public List<OrderDto> getAllByUserId(Long userId) {
        try (Connection connection = dataBaseManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_ORDERS_BY_USER_ID);
            statement.setLong(1, userId);
            return createOrderList(statement);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        throw new UnableToFindException(messageManager.getMessage("orders.unable_to_find"));
    }

    @Override
    public OrderDto getById(Long id) {
        try (Connection connection = dataBaseManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_ORDER_BY_ID);
            statement.setLong(1, id);
            return extractedFromStatement(statement);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        throw new UnableToFindException(messageManager.getMessage("order.unable_to_find_id") + id);
    }

    @Override
    public Long count() {
        try (Connection connection = dataBaseManager.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(COUNT_ORDERS);
            log.debug("DB query completed");
            Long count = null;
            if (result.next()) {
                count = result.getLong("count");
            }
            return count;
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        throw new AppException(messageManager.getMessage("count_failed"));
    }

    @Override
    public List<OrderDto> getAll() {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_ORDERS)) {
            return createOrderList(statement);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        throw new UnableToFindException(messageManager.getMessage("orders.unable_to_find"));
    }

    @Override
    public List<OrderDto> getAll(int limit, int offset) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_ORDERS_PAGED)) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            return createOrderList(statement);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        throw new UnableToFindException(messageManager.getMessage("orders.unable_to_find"));
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
                log.debug("Created order with id" + result.getLong(COL_ID));
                order.setId(result.getLong(COL_ID));
            }
            return order;
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        throw new UnableToCreateException(messageManager.getMessage("order.unable_to_create"));
    }

    @Override
    public OrderDto update(OrderDto order) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER)) {
            prepareStatementForUpdate(order, statement);
            statement.executeUpdate();
            log.debug("DB query completed");
            return order;
        } catch (SQLException e) {
            log.error("Unable to update order " + order);
            throw new UnableToUpdateException(messageManager.getMessage("order.unable_to_update"));
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
            log.error("Unable to delete order with id " + id);
        }
        throw new UnableToDeleteException(messageManager.getMessage("order.unable_to_delete"));
    }

    private OrderDto extractedFromStatement(PreparedStatement statement) throws SQLException {
        ResultSet result = statement.executeQuery();
        log.debug("DB query completed");
        OrderDto order = new OrderDto();
        if (result.next()) {
            setParameters(order, result);
        }
        return order;
    }

    private void setParameters(OrderDto order, ResultSet result) throws SQLException {
        order.setId(result.getLong(COL_ID));
        order.setUserId(result.getLong(COL_USER_ID));
        order.setOrderStatus(OrderDto.OrderStatus.valueOf(result.getString(COL_STATUS)));
        order.setPaymentMethod(OrderDto.PaymentMethod.valueOf(result.getString(COL_PAYMENT_METHOD)));
        order.setPaymentStatus(OrderDto.PaymentStatus.valueOf(result.getString(COL_PAYMENT_STATUS)));
        order.setDeliveryType(OrderDto.DeliveryType.valueOf(result.getString(COL_DELIVERY_TYPE)));
        order.setCost(result.getBigDecimal(COL_COST));
    }

    private void prepareStatementForCreate(OrderDto order, PreparedStatement statement) throws SQLException {
        statement.setLong(1, order.getUserId());
        statement.setString(2, order.getOrderStatus().toString());
        statement.setString(3, order.getPaymentMethod().toString());
        statement.setString(4, order.getPaymentStatus().toString());
        statement.setString(5, order.getDeliveryType().toString());
        statement.setBigDecimal(6, order.getCost());
    }

    private void prepareStatementForUpdate(OrderDto order, PreparedStatement statement) throws SQLException {
        statement.setLong(1, order.getUserId());
        statement.setString(2, order.getOrderStatus().toString());
        statement.setString(3, order.getPaymentMethod().toString());
        statement.setString(4, order.getPaymentStatus().toString());
        statement.setString(5, order.getDeliveryType().toString());
        statement.setBigDecimal(6, order.getCost());
        statement.setLong(7, order.getId());
    }

    private List<OrderDto> createOrderList(PreparedStatement statement) throws SQLException {
        ResultSet result = statement.executeQuery();
        List<OrderDto> orders = new ArrayList<>();
        log.debug("DB query completed");
        while (result.next()) {
            OrderDto order = new OrderDto();
            setParameters(order, result);
            orders.add(order);
        }
        return orders;
    }
}
