package online.javaclass.bookstore.data.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.MessageManager;
import online.javaclass.bookstore.data.dao.OrderDao;
import online.javaclass.bookstore.data.dto.OrderDto;
import online.javaclass.bookstore.exceptions.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Log4j2
@RequiredArgsConstructor
@Repository
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
            "WHERE o.user_id = :userId ORDER BY o.id";
    private static final String FIND_ALL_ORDERS_PAGED = "SELECT o.id, o.user_id, os.name AS status, " +
            "pm.name AS payment_method, ps.name AS payment_status, dt.name AS delivery_type, cost FROM orders o " +
            "JOIN order_status os ON o.status_id = os.id " +
            "JOIN delivery_type dt on dt.id = o.delivery_type_id " +
            "JOIN payment_method pm on o.payment_method_id = pm.id " +
            "JOIN payment_status ps on o.payment_status_id = ps.id " +
            "ORDER BY o.id LIMIT :limit OFFSET :offset";
    private static final String CREATE_ORDER = "INSERT INTO orders (user_id, status_id, payment_method_id, payment_status_id," +
            "delivery_type_id, cost) VALUES (?, " +
            "(SELECT os.id FROM order_status os WHERE os.name = ?), " +
            "(SELECT pm.id FROM payment_method pm WHERE pm.name = ?), " +
            "(SELECT ps.id FROM payment_status ps WHERE ps.name = ?), " +
            "(SELECT dt.id FROM delivery_type dt WHERE dt.name = ?), ?)";
    private static final String UPDATE_ORDER = "UPDATE orders o SET user_id = :userId, " +
            "status_id = (SELECT os.id FROM order_status os WHERE os.name = :orderStatus), " +
            "payment_method_id = (SELECT pm.id FROM payment_method pm WHERE pm.name = :paymentMethod), " +
            "payment_status_id = (SELECT ps.id FROM payment_status ps WHERE ps.name = :paymentStatus), " +
            "delivery_type_id = (SELECT dt.id FROM delivery_type dt WHERE dt.name = :daliveryType), " +
            "cost = :cost WHERE o.id = :id";
    private static final String DELETE_ORDER_BY_ID = "DELETE FROM orders WHERE id = ?";
    private static final String COUNT_ORDERS = "SELECT count(*) FROM orders";
    private static final String COL_STATUS = "status";
    private static final String COL_PAYMENT_METHOD = "payment_method";
    private static final String COL_PAYMENT_STATUS = "payment_status";
    private static final String COL_DELIVERY_TYPE = "delivery_type";
    private static final String COL_USER_ID = "user_id";
    private static final String COL_ID = "id";
    private static final String COL_COST = "cost";
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final MessageManager messageManager;

    @Override
    public List<OrderDto> getAllByUserId(Long userId) {
        try {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        return namedParameterJdbcTemplate.query(FIND_ORDERS_BY_USER_ID, params, this::process);
        } catch (DataAccessException e) {
            log.error(e.getMessage() + e);
            throw new UnableToFindException(messageManager.getMessage("orders.unable_to_find"));
        }
    }

    @Override
    public OrderDto getById(Long id) {
        try {
            return jdbcTemplate.queryForObject(FIND_ORDER_BY_ID, this::process, id);
        } catch (DataAccessException e) {
            log.error(e.getMessage() + e);
            throw new UnableToFindException(messageManager.getMessage("order.unable_to_find_id") + id);
        }
    }


    @Override
    public Long count() {
        try {
            return jdbcTemplate.queryForObject(COUNT_ORDERS, Long.class);
        } catch (DataAccessException e) {
            log.error(e.getMessage() + e);
            throw new AppException(messageManager.getMessage("count_failed"));
        }
    }

    @Override
    public List<OrderDto> getAll(int limit, int offset) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("limit", limit);
            params.put("offset", offset);
            return namedParameterJdbcTemplate.query(FIND_ALL_ORDERS_PAGED, params, this::process);
        } catch (DataAccessException e) {
            log.error(e.getMessage() + e);
            throw new UnableToFindException(messageManager.getMessage("orders.unable_to_find"));
        }
    }

    @Override
    public OrderDto create(OrderDto order) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> getPreparedStatement(order, connection), keyHolder);
            long id = (long)Objects.requireNonNull(keyHolder.getKeys()).get("id");
            return getById(id);
        } catch (DataAccessException e) {
            log.error(e.getMessage() + e);
            throw new UnableToCreateException(messageManager.getMessage("order.unable_to_create"));
        }
    }

    @Override
    public OrderDto update(OrderDto order) {
        try {
            namedParameterJdbcTemplate.update(UPDATE_ORDER, getParamMap(order));
            return getById(order.getId());
        } catch (DataAccessException e) {
            log.error(e.getMessage() + e);
            throw new UnableToUpdateException(messageManager.getMessage("order.unable_to_update"));
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            return 1 == jdbcTemplate.update(DELETE_ORDER_BY_ID, id);
        } catch (DataAccessException e) {
            log.error(e.getMessage() + e);
            throw new UnableToDeleteException(messageManager.getMessage("order.unable_to_delete"));
        }

    }

    private OrderDto process(ResultSet rs, int rowNum) throws SQLException {
        OrderDto order = new OrderDto();
        order.setId(rs.getLong(COL_ID));
        order.setUserId(rs.getLong(COL_USER_ID));
        order.setOrderStatus(OrderDto.OrderStatus.valueOf(rs.getString(COL_STATUS)));
        order.setPaymentMethod(OrderDto.PaymentMethod.valueOf(rs.getString(COL_PAYMENT_METHOD)));
        order.setPaymentStatus(OrderDto.PaymentStatus.valueOf(rs.getString(COL_PAYMENT_STATUS)));
        order.setDeliveryType(OrderDto.DeliveryType.valueOf(rs.getString(COL_DELIVERY_TYPE)));
        order.setCost(rs.getBigDecimal(COL_COST));
        return order;
    }

    private PreparedStatement getPreparedStatement(OrderDto order, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(CREATE_ORDER, Statement.RETURN_GENERATED_KEYS);
        ps.setLong(1, order.getUserId());
        ps.setString(2, order.getOrderStatus().toString());
        ps.setString(3, order.getPaymentMethod().toString());
        ps.setString(4, order.getPaymentStatus().toString());
        ps.setString(5, order.getDeliveryType().toString());
        ps.setBigDecimal(6, order.getCost());
        return ps;
    }

    private Map<String, Object> getParamMap(OrderDto order) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", order.getId());
        params.put("userId", order.getUserId());
        params.put("status", order.getOrderStatus());
        params.put("paymentMethod", order.getPaymentMethod().toString());
        params.put("paymentStatus", order.getPaymentStatus());
        params.put("deliveryType", order.getDeliveryType());
        params.put("cost", order.getCost());
        return params;
    }
}
