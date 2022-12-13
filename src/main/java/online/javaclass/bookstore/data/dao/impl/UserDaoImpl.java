package online.javaclass.bookstore.data.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.data.connection.DataBaseManager;
import online.javaclass.bookstore.data.dao.UserDao;
import online.javaclass.bookstore.data.dto.UserDto;
import online.javaclass.bookstore.service.exceptions.UnableToCreateException;
import online.javaclass.bookstore.service.exceptions.UnableToDeleteException;
import online.javaclass.bookstore.service.exceptions.UnableToFindException;
import online.javaclass.bookstore.service.exceptions.UnableToUpdateException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
    private static final String CREATE_USER = "INSERT INTO users (firstname, lastname, email, password, role_id, " +
            "rating) VALUES (?, ?, ?, ?, (SELECT r.id FROM roles r WHERE r.id = ?), ?)";
    private static final String UPDATE_USER = "UPDATE users SET firstname = ?, lastname = ?, email = ?, " +
            "password = ?, role_id = ?, rating = ? WHERE id = ?";
    private static final String FIND_USER_BY_ID = "SELECT u.id, u.firstname, u.lastname, u.email, " +
            "u.password, r.name AS role, u.rating FROM users u JOIN roles r ON u.role_id = r.id " +
            "WHERE u.id = ?";
    private static final String FIND_USER_BY_EMAIL = "SELECT u.id, u.firstname, u.lastname, u.email, " +
            "u.password, r.name AS role, u.rating FROM users u " +
            "JOIN roles r ON u.role_id = r.id WHERE email = ?";
    private static final String FIND_ALL_USERS = "SELECT u.id, u.firstname, u.lastname, u.email, " +
            "u.password, r.name AS role, u.rating FROM users u " +
            "JOIN roles r ON u.role_id = r.id";
    private static final String FIND_USERS_BY_LASTNAME = "SELECT u.id, u.firstname, u.lastname, u.email, " +
            "u.password, r.name AS role, u.rating FROM users u JOIN roles r ON u.role_id = r.id " +
            "WHERE lastname = ?";
    private static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE id = ?";
    private static final String COUNT_USERS = "SELECT count(*) FROM users";
    private static final String COL_USER_ID = "id";
    private static final String COL_FIRSTNAME = "firstname";
    private static final String COL_LASTNAME = "lastname";
    private static final String COL_EMAIL = "email";
    private static final String COL_USER_PASSWORD = "password";
    private static final String COL_USER_ROLE = "role";
    private static final String COL_RATING = "rating";

    private final DataBaseManager dataBaseManager;

    public UserDto create(UserDto user) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatementForCreate(user, statement);
            statement.executeUpdate();
            log.debug("DB query completed");
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                user.setId(result.getLong(COL_USER_ID));
            }
            return findById(result.getLong(COL_USER_ID));
        } catch (SQLException e) {
            throw new UnableToCreateException("Creation failed! " + user, e);
        }
    }

    public UserDto update(UserDto user) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {
            prepareStatementForUpdate(user, statement);
            statement.executeUpdate();
            log.debug("DB query completed");
            return findById(user.getId());
        } catch (SQLException e) {
            throw new UnableToUpdateException("Update failed! " + user + " " + e.getMessage());
        }
    }

    public UserDto findById(Long id) {
        UserDto user = new UserDto();
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            setParameters(user, result);
            log.debug("DB query completed");
            return user;
        } catch (SQLException e) {
            throw new UnableToFindException("Unable to find user with id " + id, e);
        }
    }

    public UserDto findByEmail(String email) {
        UserDto user = new UserDto();
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_EMAIL)) {
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
            log.debug("DB query completed");
            setParameters(user, result);
            return user;
        } catch (SQLException e) {
            throw new UnableToFindException("Unable to find user with email " + email, e);
        }
    }

    public List<UserDto> findByLastName(String lastName) {
        List<UserDto> users = new ArrayList<>();
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USERS_BY_LASTNAME)) {
            statement.setString(1, lastName);
            ResultSet result = statement.executeQuery();
            log.debug("DB query completed");
            while (result.next()) {
                UserDto userDto = new UserDto();
                setParameters(userDto, result);
                users.add(userDto);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to find users with lastname " + lastName, e);
        }
    }

    public List<UserDto> findAll() {
        List<UserDto> users = new ArrayList<>();
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS)) {
            ResultSet result = statement.executeQuery();
            log.debug("DB query completed");
            while (result.next()) {
                UserDto userDto = new UserDto();
                setParameters(userDto, result);
                users.add(userDto);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("No users found", e);
        }
    }

    public boolean deleteById(Long id) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER_BY_ID)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            log.debug("DB query completed");
            return affectedRows == 1;
        } catch (SQLException e) {
            throw new UnableToDeleteException("Unable to delete user with id " + id, e);
        }
    }

    public Long count() {
        try (Connection connection = dataBaseManager.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(COUNT_USERS);
            log.debug("DB query completed");
            result.next();
            return result.getLong("count");
        } catch (SQLException e) {
            throw new RuntimeException("Count failed!", e);
        }
    }

    private void setParameters(UserDto user, ResultSet result) throws SQLException {
        while (result.next()) {
            user.setId(result.getLong(COL_USER_ID));
            user.setFirstName(result.getString(COL_FIRSTNAME));
            user.setLastName(result.getString(COL_LASTNAME));
            user.setEmail(result.getString(COL_EMAIL));
            user.setPassword(result.getString(COL_USER_PASSWORD));
            user.setRole(UserDto.Role.values()[(result.getInt(COL_USER_ROLE))]);
            user.setRating(result.getBigDecimal(COL_RATING));
        }
    }

    private void prepareStatementForCreate(UserDto user, PreparedStatement statement) throws SQLException {
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getPassword());
        statement.setInt(5, user.getRole().ordinal() + 1);
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
}