package online.javaclass.bookstore.data.dao.impl;

import online.javaclass.bookstore.data.connection.DataBaseManager;
import online.javaclass.bookstore.data.dao.UserDao;
import online.javaclass.bookstore.data.entities.Role;
import online.javaclass.bookstore.data.entities.User;
import online.javaclass.bookstore.service.exceptions.UnableToCreateException;
import online.javaclass.bookstore.service.exceptions.UnableToDeleteException;
import online.javaclass.bookstore.service.exceptions.UnableToFindException;
import online.javaclass.bookstore.service.exceptions.UnableToUpdateException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    public static final String CREATE_USER = "INSERT INTO users (firstname, lastname, email, user_password, user_role, rating) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_USER = "UPDATE users SET firstname = ?, lastname = ?, email = ?, user_password = ?, user_role = ?, rating = ? WHERE user_id = ?";
    public static final String FIND_USER_BY_ID = "SELECT user_id, firstname, lastname, email, user_password, user_role, rating FROM users WHERE user_id = ?";
    public static final String FIND_USER_BY_EMAIL = "SELECT user_id, firstname, lastname, email, user_password, user_role, rating FROM users WHERE email = ?";
    public static final String FIND_ALL = "SELECT user_id, firstname, lastname, email, user_password, user_role, rating FROM users";
    public static final String FIND_USERS_BY_LASTNAME = "SELECT user_id, firstname, lastname, email, user_password, user_role, rating FROM users WHERE lastname = ?";
    public static final String DELETE_BY_ID = "DELETE FROM users WHERE user_id = ?";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_FIRSTNAME = "firstname";
    public static final String COL_LASTNAME = "lastname";
    public static final String COL_EMAIL = "email";
    public static final String COL_USER_PASSWORD = "user_password";
    public static final String COL_USER_ROLE = "user_role";
    public static final String COL_RATING = "rating";
    private final DataBaseManager dataBaseManager;

    private static final Logger log = LogManager.getLogger();

    public UserDaoImpl(DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
    }

    public User create(User user) {

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


    public User update(User user) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {
            prepareStatementForUpdate(user, statement);
            int affectedRows = statement.executeUpdate();
            log.debug("DB query completed");
            if (affectedRows > 0) {
                System.out.println("Valid state : " + findById(user.getId()));
            }
            return findById(user.getId());
        } catch (SQLException e) {
            throw new UnableToUpdateException("Update failed! " + user, e);
        }
    }


    public User findById(Long id) {
        User user = new User();
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            log.debug("DB query completed");
            setParameters(user, result);
            return user;
        } catch (SQLException e) {
            throw new UnableToFindException("Unable to find user with id " + id, e);
        }
    }

    public User findByEmail(String email) {
        User user = new User();
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

    public List<User> findByLastName(String lastName) {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USERS_BY_LASTNAME)) {
            statement.setString(1, lastName);
            ResultSet result = statement.executeQuery();
            log.debug("DB query completed");
            while (result.next()) {
                long id = result.getLong("user_id");
                User user = findById(id);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to find users with lastname " + lastName, e);
        }
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
            ResultSet result = statement.executeQuery();
            log.debug("DB query completed");
            while (result.next()) {
                long id = result.getLong("user_id");
                User user = findById(id);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("No users found", e);
        }
    }

    public boolean deleteById(Long id) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            log.debug("DB query completed");
            if (affectedRows == 1) {
                return true;
            } else {
                System.out.println("No such user found!");
                return false;
            }
        } catch (SQLException e) {
            throw new UnableToDeleteException("Unable to delete user with id " + id, e);
        }
    }

    public Long count() {
        try (Connection connection = dataBaseManager.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery("SELECT count(*) FROM users");
            log.debug("DB query completed");
            result.next();
            return result.getLong("count");
        } catch (SQLException e) {
            throw new RuntimeException("Count failed!", e);
        }
    }

    private void setParameters(User user, ResultSet result) {
        try {
            while (result.next()) {
                user.setId(result.getLong(COL_USER_ID));
                user.setFirstName(result.getString(COL_FIRSTNAME));
                user.setLastName(result.getString(COL_LASTNAME));
                user.setEmail(result.getString(COL_EMAIL));
                user.setPassword(result.getString(COL_USER_PASSWORD));
                verifyAndSetRole(user, result.getString(COL_USER_ROLE));
                user.setRating(result.getBigDecimal(COL_RATING));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to set parameters to user " + user, e);
        }
    }

    private void verifyAndSetRole(User user, String role) {
        switch (role) {
            case "admin" -> user.setRole(Role.ADMIN);
            case "manager" -> user.setRole(Role.MANAGER);
            case "user" -> user.setRole(Role.USER);
        }
    }

    private void prepareStatementForCreate(User user, PreparedStatement statement) throws SQLException {
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getPassword());
        statement.setString(5, user.getRole().getTitle());
        statement.setBigDecimal(6, user.getRating());
    }

    private void prepareStatementForUpdate(User user, PreparedStatement statement) throws SQLException {
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getPassword());
        statement.setString(5, user.getRole().getTitle());
        statement.setBigDecimal(6, user.getRating());
        statement.setLong(7, user.getId());
    }
}