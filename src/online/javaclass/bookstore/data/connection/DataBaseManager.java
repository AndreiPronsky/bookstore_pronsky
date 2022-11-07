package online.javaclass.bookstore.data.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseManager {
    private final ProxyConnection connection;

    public DataBaseManager(String url, String user, String password) {
        try {
            this.connection = new ProxyConnection(DriverManager.getConnection(url, user, password));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        try {
            connection.reallyClose();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
