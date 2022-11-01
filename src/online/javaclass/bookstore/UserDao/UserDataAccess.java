package online.javaclass.bookstore.UserDao;

import online.javaclass.bookstore.Entities.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserDataAccess {
    public static final String URL = "jdbc:postgresql://127.0.0.1:5432/bookstore";
    public static final String USER = "postgres";
    public static final String PASSWORD = "root";
    public static final String CREATE_USER = "INSERT INTO users (firstname, lastname, email, user_password, user_role) VALUES (?, ?, ?, ?, ?)";
    public static final String UPDATE_USER = "SELECT user_id, firstname, lastname, email, user_password, user_role FROM users WHERE user_id = ?";
    public static final String FIND_USER_BY_ID = "SELECT user_id, firstname, lastname, email, user_password, user_role, rating FROM users WHERE user_id = ?";
    public static final String FIND_ALL = "SELECT user_id, firstname, lastname, email, user_password, user_role, rating FROM users";
    public static final String DELETE_BY_ID = "DELETE FROM users WHERE user_id = ?";

    public static void create(User user) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS);
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

    public static void update(User user) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter column label you want to modify: ");
            String column = scanner.next();
            System.out.print("Enter new value: ");
            String newValue = scanner.next();
            PreparedStatement statement = connection.prepareStatement(UPDATE_USER,
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
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

    public static User find(Long id) {
        User user = new User();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID);
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

    public static List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL);
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

    public static boolean deleteById(Long id) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID);
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