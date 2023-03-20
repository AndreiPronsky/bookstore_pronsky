package online.javaclass.bookstore.data.dao.impl;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.platform.MessageManager;
import online.javaclass.bookstore.data.dao.UserDao;
import online.javaclass.bookstore.data.entities.User;
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

@RequiredArgsConstructor
@Repository
public class UserDaoImpl implements UserDao {
    private static final String CREATE_USER = "INSERT INTO users (firstname, lastname, email, password, role_id, " +
            "rating) VALUES (?, ?, ?, ?, (SELECT r.id FROM roles r WHERE r.name = ?), ?)";
    private static final String UPDATE_USER = "UPDATE users SET firstname = :firstname, lastname = :lastname, " +
            "email = :email, \"password\" = :password, role_id = :role, rating = :rating WHERE id = :id";
    private static final String FIND_USER_BY_ID = "SELECT u.id, u.firstname, u.lastname, u.email, u.password, " +
            "r.name AS role, u.rating FROM users u JOIN roles r ON u.role_id = r.id WHERE u.id = ?";
    private static final String FIND_USER_BY_EMAIL = "SELECT u.id, u.firstname, u.lastname, u.email, u.password, " +
            "r.name AS role, u.rating FROM users u JOIN roles r ON u.role_id = r.id WHERE u.email = ?";
    private static final String FIND_ALL_USERS_PAGED = "SELECT u.id, u.firstname, u.lastname, u.email, u.password, " +
            "r.name AS role, u.rating FROM users u JOIN roles r ON u.role_id = r.id " +
            "ORDER BY u.id LIMIT :limit OFFSET :offset";
    private static final String FIND_USERS_BY_LASTNAME_PAGED = "SELECT u.id, u.firstname, u.lastname, u.email, u.password, " +
            "r.name AS role, u.rating FROM users u JOIN roles r ON u.role_id = r.id WHERE lastname = :lastname " +
            "ORDER BY u.id LIMIT :limit OFFSET :offset";
    private static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE id = ?";
    private static final String COUNT_USERS = "SELECT count(*) FROM users";
    private static final String COL_ID = "id";
    private static final String COL_FIRSTNAME = "firstname";
    private static final String COL_LASTNAME = "lastname";
    private static final String COL_EMAIL = "email";
    private static final String COL_PASSWORD = "password";
    private static final String COL_ROLE = "role";
    private static final String COL_RATING = "rating";
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final MessageManager messageManager;

    @LogInvocation
    @Override
    public User create(User user) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> getPreparedStatement(user, connection), keyHolder);
            long id = (long) Objects.requireNonNull(keyHolder.getKeys()).get("id");
            return getById(id);
        } catch (DataAccessException e) {
            throw new UnableToCreateException(messageManager.getMessage("user.unable_to_create"));
        }
    }

    @LogInvocation
    @Override
    public User update(User user) {
        try {
            namedParameterJdbcTemplate.update(UPDATE_USER, getParamMap(user));
            return getById(user.getId());
        } catch (DataAccessException e) {
            throw new UnableToUpdateException(messageManager.getMessage("user.unable_to_update"));
        }
    }

    @LogInvocation
    @Override
    public User getById(Long id) {
        try {
            return jdbcTemplate.queryForObject(FIND_USER_BY_ID, this::process, id);
        } catch (DataAccessException e) {
            throw new UnableToFindException(messageManager.getMessage("user.unable_to_find_id") + id);
        }
    }

    @LogInvocation
    @Override
    public User getByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject(FIND_USER_BY_EMAIL, this::process, email);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @LogInvocation
    @Override
    public List<User> getByLastName(String lastName, int limit, int offset) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("lastname", lastName);
            params.put("limit", limit);
            params.put("offset", offset);
            return namedParameterJdbcTemplate.query(FIND_USERS_BY_LASTNAME_PAGED, params, this::process);
        } catch (DataAccessException e) {
            throw new UnableToFindException(messageManager.getMessage("users.unable_to_find_lastname") + lastName);
        }
    }

    @LogInvocation
    @Override
    public List<User> getAll(int limit, int offset) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("limit", limit);
            params.put("offset", offset);
            return namedParameterJdbcTemplate.query(FIND_ALL_USERS_PAGED, params, this::process);
        } catch (DataAccessException e) {
            throw new UnableToFindException(messageManager.getMessage("users.not_found"));
        }
    }

    @LogInvocation
    @Override
    public boolean deleteById(Long id) {
        try {
            return 1 == jdbcTemplate.update(DELETE_USER_BY_ID, id);
        } catch (DataAccessException e) {
            throw new UnableToDeleteException(messageManager.getMessage("user.unable_to_delete"));
        }
    }

    @Override
    public Long count() {
        try {
            return jdbcTemplate.queryForObject(COUNT_USERS, Long.class);
        } catch (DataAccessException e) {
            throw new AppException(messageManager.getMessage("count_failed"));
        }
    }

    private PreparedStatement getPreparedStatement(User user, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, user.getFirstName());
        ps.setString(2, user.getLastName());
        ps.setString(3, user.getEmail());
        ps.setString(4, user.getPassword());
        ps.setString(5, user.getRole().toString());
        ps.setBigDecimal(6, user.getRating());
        return ps;
    }

    private User process(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(COL_ID));
        user.setFirstName(rs.getString(COL_FIRSTNAME));
        user.setLastName(rs.getString(COL_LASTNAME));
        user.setEmail(rs.getString(COL_EMAIL));
        user.setPassword(rs.getString(COL_PASSWORD));
        user.setRole(User.Role.valueOf(rs.getString(COL_ROLE)));
        user.setRating(rs.getBigDecimal(COL_RATING));
        return user;
    }

    private Map<String, Object> getParamMap(User user) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", user.getId());
        params.put("email", user.getEmail());
        params.put("password", user.getPassword());
        params.put("firstname", user.getFirstName());
        params.put("lastname", user.getLastName());
        params.put("role", user.getRole().ordinal());
        params.put("rating", user.getRating());
        return params;
    }
}