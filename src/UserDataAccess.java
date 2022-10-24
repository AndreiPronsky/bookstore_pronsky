import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserDataAccess {
    public static final String URL = "jdbc:postgresql://127.0.0.1:5432/bookstore";
    public static final String USER = "postgres";
    public static final String PASSWORD = "root";

    static void create(User user) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users (firstname, lastname, email, user_password, user_role) VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setInt(5, user.getRole());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void update(User user) {

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner scanner = new Scanner(System.in)) {
            long id = user.getId();
            System.out.print("Enter column label you want to modify: ");
            String column = scanner.next();
            System.out.print("Enter new value: ");
            String newValue = scanner.next();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT user_id, firstname, lastname, email, user_password, user_role FROM users WHERE user_id = ?",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                result.updateString(column, newValue);
                result.updateRow();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static User find(Long id) {
        User user = new User();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT user_id, firstname, lastname, email, user_password, user_role, rating FROM users WHERE user_id = ?");
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    static List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT user_id, firstname, lastname, email, user_password, user_role, rating FROM users");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                long id = result.getLong("user_id");
                String fName = result.getString("firstname");
                String lName = result.getString("lastname");
                String email = result.getString("email");
                String password = result.getString("user_password");
                int role = result.getInt("user_role");
                BigDecimal rating = result.getBigDecimal("rating");
                User user = new User(id, fName, lName, email, password, role, rating);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    static boolean deleteById(Long id) {
        boolean isDeleted = false;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM users WHERE user_id = ?");
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                isDeleted = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDeleted;
    }

    static void printTableInfo() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM users");
            ResultSetMetaData meta = result.getMetaData();
            System.out.printf("------------------------------------------------------------------------%n");
            System.out.printf("                                " + meta.getTableName(1) + "%n");
            System.out.printf("------------------------------------------------------------------------%n");
            System.out.printf("| %-20s | %-12s | %-16s | %-11s |%n", "COLUMN", "TYPE", "IS AUTOINCREMENT", "IS NULLABLE");
            System.out.printf("------------------------------------------------------------------------%n");
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                boolean isNullable = meta.isNullable(i) == ResultSetMetaData.columnNullable;
                System.out.printf("| %-20s | %-12s | %-16s | %-11b |%n", meta.getColumnName(i), meta.getColumnTypeName(i),
                        meta.isAutoIncrement(i), isNullable);
            }
            System.out.printf("------------------------------------------------------------------------%n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}