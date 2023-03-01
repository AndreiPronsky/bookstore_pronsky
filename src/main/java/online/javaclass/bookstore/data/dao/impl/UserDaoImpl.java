package online.javaclass.bookstore.data.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.MessageManager;
import online.javaclass.bookstore.data.dao.connection.DataBaseManager;
import online.javaclass.bookstore.data.dao.UserDao;
import online.javaclass.bookstore.data.dto.UserDto;
import online.javaclass.bookstore.exceptions.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Repository
public class UserDaoImpl implements UserDao {
    private static final String CREATE_USER = "INSERT INTO users (firstname, lastname, email, password, role_id, " +
            "rating) VALUES (?, ?, ?, ?, (SELECT r.id FROM roles r WHERE r.name = ?), ?)";
    private static final String UPDATE_USER = "UPDATE users SET firstname = ?, lastname = ?, email = ?, " +
            "password = ?, role_id = ?, rating = ? WHERE id = ?";
    private static final String FIND_USER_BY_ID = "SELECT u.id, u.firstname, u.lastname, u.email, u.password, " +
            "r.name AS role, u.rating FROM users u JOIN roles r ON u.role_id = r.id WHERE u.id = ?";
    private static final String FIND_USER_BY_EMAIL = "SELECT u.id, u.firstname, u.lastname, u.email, u.password, " +
            "r.name AS role, u.rating FROM users u JOIN roles r ON u.role_id = r.id WHERE u.email = ?";
    private static final String FIND_ALL_USERS_PAGED = "SELECT u.id, u.firstname, u.lastname, u.email, u.password, " +
            "r.name AS role, u.rating FROM users u JOIN roles r ON u.role_id = r.id " +
            "ORDER BY u.id LIMIT ? OFFSET ?";
    private static final String FIND_USERS_BY_LASTNAME_PAGED = "SELECT u.id, u.firstname, u.lastname, u.email, u.password, " +
            "r.name AS role, u.rating FROM users u JOIN roles r ON u.role_id = r.id WHERE lastname = ? " +
            "ORDER BY u.id LIMIT ? OFFSET ?";
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

    private final DataBaseManager dataBaseManager;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final MessageManager messageManager;

    @Override
    public UserDto create(UserDto user) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatementForCreate(user, statement);
            statement.executeUpdate();
            log.debug("DB query completed");
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                log.debug("Created user with id" + result.getLong(COL_ID));
                user.setId(result.getLong(COL_ID));
            }
            return user;
        } catch (SQLException e) {
            log.error(e.getMessage() + e);
            if (e.getMessage().startsWith("ERROR: duplicate key value violates unique constraint")) {
                throw new LoginException(messageManager.getMessage("error.email_in_use"));
            }
            throw new UnableToCreateException(messageManager.getMessage("user.unable_to_create"));
        }
    }

    @Override
    public UserDto update(UserDto user) {
        try {
            namedParameterJdbcTemplate.update(UPDATE_USER, getParamMap(user));
            return getById(user.getId());
        } catch (DataAccessException e) {
            log.error(e.getMessage() + e);
            throw new UnableToUpdateException(messageManager.getMessage("user.unable_to_update"));
        }
    }

    @Override
    public UserDto getById(Long id) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID)) {
            statement.setLong(1, id);
            return extractedFromStatement(statement);
        } catch (SQLException e) {
            log.error(e.getMessage() + e);
            throw new UnableToFindException(messageManager.getMessage("user.unable_to_find_id") + id);
        }
    }

    @Override
    public UserDto getByEmail(String email) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_EMAIL)) {
            statement.setString(1, email);
            return extractedFromStatement(statement);
        } catch (SQLException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<UserDto> getByLastName(String lastName, int limit, int offset) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USERS_BY_LASTNAME_PAGED)) {
            statement.setString(1, lastName);
            statement.setInt(2, limit);
            statement.setInt(3, offset);
            return createUserList(statement);
        } catch (SQLException e) {
            log.error(e.getMessage() + e);
            throw new UnableToFindException(messageManager.getMessage("users.unable_to_find_lastname") + lastName);
        }
    }

    @Override
    public List<UserDto> getAll(int limit, int offset) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS_PAGED)) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            return createUserList(statement);
        } catch (SQLException e) {
            log.error(e.getMessage() + e);
            throw new UnableToFindException(messageManager.getMessage("users.not_found"));
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER_BY_ID)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            log.debug("DB query completed");
            return affectedRows == 1;
        } catch (SQLException e) {
            log.error(e.getMessage() + e);
            throw new UnableToDeleteException(messageManager.getMessage("user.unable_to_delete"));
        }
    }

    @Override
    public Long count() {
        try (Connection connection = dataBaseManager.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(COUNT_USERS);
            log.debug("DB query completed");
            Long count = null;
            if (result.next()) {
                count = result.getLong("count");
            }
            return count;
        } catch (SQLException e) {
            log.error(e.getMessage() + e);
            throw new AppException(messageManager.getMessage("count_failed"));
        }
    }

    private UserDto extractedFromStatement(PreparedStatement statement) throws SQLException {
        ResultSet result = statement.executeQuery();
        log.debug("DB query completed");
        UserDto user = null;
        if (result.next()) {
            user = new UserDto();
            setParameters(user, result);
        }
        return user;
    }

    private List<UserDto> createUserList(PreparedStatement statement) throws SQLException {
        ResultSet result = statement.executeQuery();
        log.debug("DB query completed");
        List<UserDto> users = new ArrayList<>();
        while (result.next()) {
            UserDto userDto = new UserDto();
            setParameters(userDto, result);
            users.add(userDto);
        }
        return users;
    }

    private void setParameters(UserDto user, ResultSet result) throws SQLException {
        user.setId(result.getLong(COL_ID));
        user.setFirstName(result.getString(COL_FIRSTNAME));
        user.setLastName(result.getString(COL_LASTNAME));
        user.setEmail(result.getString(COL_EMAIL));
        user.setPassword(result.getString(COL_PASSWORD));
        user.setRole(UserDto.Role.valueOf(result.getString(COL_ROLE)));
        user.setRating(result.getBigDecimal(COL_RATING));
    }

    private void prepareStatementForCreate(UserDto user, PreparedStatement statement) throws SQLException {
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getPassword());
        statement.setString(5, user.getRole().toString());
        statement.setBigDecimal(6, user.getRating());
    }

    private void prepareStatementForUpdate(UserDto user, PreparedStatement statement) throws SQLException {
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getPassword());
        statement.setInt(5, user.getRole().ordinal() + 1);
        statement.setBigDecimal(6, user.getRating());
        statement.setLong(7, user.getId());
    }

    private Map<String, Object> getParamMap(UserDto user) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", user.getId());
        params.put("email", user.getEmail());
        params.put("password", user.getPassword());
        params.put("firstname", user.getFirstName());
        params.put("lastname", user.getLastName());
        params.put("role", user.getRole().toString());
        params.put("rating", user.getRating());
        return params;
    }
}