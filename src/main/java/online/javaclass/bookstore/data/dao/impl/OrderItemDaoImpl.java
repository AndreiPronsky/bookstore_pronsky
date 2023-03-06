package online.javaclass.bookstore.data.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.MessageManager;
import online.javaclass.bookstore.data.dao.OrderItemDao;
import online.javaclass.bookstore.data.dto.OrderItemDto;
import online.javaclass.bookstore.exceptions.UnableToCreateException;
import online.javaclass.bookstore.exceptions.UnableToDeleteException;
import online.javaclass.bookstore.exceptions.UnableToFindException;
import online.javaclass.bookstore.exceptions.UnableToUpdateException;
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

@RequiredArgsConstructor
@Log4j2
@Repository
public class OrderItemDaoImpl implements OrderItemDao {
    private static final String FIND_ITEMS_BY_ORDER_ID = "SELECT id, order_id, book_id, quantity, price FROM order_items" +
            " WHERE order_id = ?";
    private static final String FIND_ITEM_BY_ID = "SELECT id, order_id, book_id, quantity, price FROM order_items " +
            "WHERE id = ?";
    private static final String FIND_ALL_PAGED = "SELECT id, order_id, book_id, quantity, price FROM order_items " +
            "LIMIT :limit OFFSET :offset";
    private static final String CREATE_ITEM = "INSERT INTO order_items (order_id, book_id, quantity, price) " +
            "VALUES (?, ?, ?, ?)";
    private static final String UPDATE_ITEM = "UPDATE order_items SET order_id = :orderId, book_id = :bookId, " +
            "quantity = :quantity, price = :price WHERE id = :id";
    private static final String DELETE_ITEM_BY_ID = "DELETE FROM order_items WHERE id = ?";
    private static final String COL_ID = "id";
    private static final String COL_ORDER_ID = "order_id";
    private static final String COL_BOOK_ID = "book_id";
    private static final String COL_QUANTITY = "quantity";
    private static final String COL_PRICE = "price";
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final MessageManager messageManager;

    @Override
    public List<OrderItemDto> getAllByOrderId(Long orderId) {
        try {
            return jdbcTemplate.query(FIND_ITEMS_BY_ORDER_ID, this::process, orderId);
        } catch (DataAccessException e) {
            log.error(e.getMessage() + e);
            throw new UnableToFindException(messageManager.getMessage("items.not_found"));
        }
    }

    @Override
    public void deleteAllByOrderId(Long orderId) {
        for (OrderItemDto item : getAllByOrderId(orderId)) {
            deleteById(item.getId());
        }
    }

    @Override
    public OrderItemDto getById(Long id) {
        try {
            return jdbcTemplate.queryForObject(FIND_ITEM_BY_ID, this::process, id);
        } catch (DataAccessException e) {
            log.error(e.getMessage() + e);
            throw new UnableToFindException(messageManager.getMessage("items.not_found"));
        }
    }

    @Override
    public List<OrderItemDto> getAll(int limit, int offset) {
        try {
            Map<String, Integer> params = new HashMap<>();
            params.put("limit", limit);
            params.put("offset", offset);
            return namedParameterJdbcTemplate.query(FIND_ALL_PAGED, params, this::process);
        } catch (DataAccessException e) {
            log.error(e.getMessage() + e);
            throw new UnableToFindException(messageManager.getMessage("items.not_found"));
        }

    }

    @Override
    public OrderItemDto create(OrderItemDto item) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> getPreparedStatement(item, connection), keyHolder);
            long id = (long) Objects.requireNonNull(keyHolder.getKeys()).get("id");
            return getById(id);
        } catch (DataAccessException e) {
            log.error(e.getMessage() + e);
            throw new UnableToCreateException(messageManager.getMessage("item.unable_to_create"));
        }
    }

    @Override
    public OrderItemDto update(OrderItemDto item) {
        try {
            namedParameterJdbcTemplate.update(UPDATE_ITEM, getParamMap(item));
            return getById(item.getId());
        } catch (DataAccessException e) {
            log.error(e.getMessage() + e);
            throw new UnableToUpdateException(messageManager.getMessage("item.unable_to_update"));
        }

    }

    @Override
    public boolean deleteById(Long id) {
        try {
            return 1 == jdbcTemplate.update(DELETE_ITEM_BY_ID, id);
        } catch (DataAccessException e) {
            log.error(e.getMessage() + e);
            throw new UnableToDeleteException(messageManager.getMessage("item.unable_to_delete"));
        }
    }

    private OrderItemDto process(ResultSet rs, int rowNum) throws SQLException {
        OrderItemDto item = new OrderItemDto();
        item.setId(rs.getLong(COL_ID));
        item.setOrderId(rs.getLong(COL_ORDER_ID));
        item.setBookId(rs.getLong(COL_BOOK_ID));
        item.setQuantity(rs.getInt(COL_QUANTITY));
        item.setPrice(rs.getBigDecimal(COL_PRICE));
        return item;
    }

    private PreparedStatement getPreparedStatement(OrderItemDto item, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(CREATE_ITEM, Statement.RETURN_GENERATED_KEYS);
        ps.setLong(1, item.getOrderId());
        ps.setLong(2, item.getBookId());
        ps.setInt(3, item.getQuantity());
        ps.setBigDecimal(4, item.getPrice());
        return ps;
    }

    private Map<String, Object> getParamMap(OrderItemDto item) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", item.getId());
        params.put("orderId", item.getOrderId());
        params.put("bookId", item.getBookId());
        params.put("quantity", item.getQuantity());
        params.put("price", item.getPrice());
        return params;
    }
}
