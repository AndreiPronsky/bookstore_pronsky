package online.javaclass.bookstore.data.dao.daoImpl;

import online.javaclass.bookstore.data.connection.DataBaseManager;
import online.javaclass.bookstore.data.dao.UserDao;
import online.javaclass.bookstore.data.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserDaoImpl implements UserDao {
    public static final String CREATE_USER = "INSERT INTO users (firstname, lastname, email, user_password, user_role) VALUES (?, ?, ?, ?, ?)";
    public static final String UPDATE_USER = "SELECT user_id, firstname, lastname, email, user_password, user_role FROM users WHERE user_id = ?";
    public static final String FIND_USER_BY_ID = "SELECT user_id, firstname, lastname, email, user_password, user_role, rating FROM users WHERE user_id = ?";
    public static final String FIND_ALL = "SELECT user_id, firstname, lastname, email, user_password, user_role, rating FROM users";
    public static final String DELETE_BY_ID = "DELETE FROM users WHERE user_id = ?";
    private final DataBaseManager dataBaseManager;

    public UserDaoImpl(DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
    }

    public void create(User user) {
        Connection connection = dataBaseManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setInt(5, user.getRole());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getLong("user_id"));
                System.out.println("Created : " + find(resultSet.getLong("user_id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Creation failed!");
        }
    }

    public void update(User user) {
        Connection connection = dataBaseManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
             Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter column label you want to modify: ");
            String column = scanner.next();
            System.out.print("Enter new value: ");
            String newValue = scanner.next();
            statement.setLong(1, user.getId());
            statement.executeQuery();
            ResultSet result = statement.getResultSet();
            while (result.next()) {
                result.updateString(column, newValue);
                result.updateRow();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Update failed!");
        }
        System.out.println("Valid state : " + find(user.getId()));
    }

    public User find(Long id) {
        Connection connection = dataBaseManager.getConnection();
        User user = new User();
        try (PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                user.setId(result.getLong("user_id"));
                user.setFirstName(result.getString("firstname"));
                user.setLastName(result.getString("lastname"));
                user.setEmail(result.getString("email"));
                user.setPassword(result.getString("user_password"));
                user.setRole(result.getInt("user_role"));
                user.setRating(result.getBigDecimal("rating"));
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException("No such user found!");
        }
    }

    public List<User> findAll() {
        Connection connection = dataBaseManager.getConnection();
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                long id = result.getLong("user_id");
                User user = find(id);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public boolean deleteById(Long id) {
        Connection connection = dataBaseManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 1) {
                System.out.println("Deleted user with id : " + id);
                return true;
            } else {
                System.out.println("No such user found!");
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to delete!");
        }
    }
}