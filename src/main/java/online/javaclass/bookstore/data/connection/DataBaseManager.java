package online.javaclass.bookstore.data.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseManager {
    private final ProxyConnection connection;
    static Logger logger = LogManager.getLogger();

    public DataBaseManager(String url, String user, String password) {
        try {
            this.connection = new ProxyConnection(DriverManager.getConnection(url, user, password));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        logger.info("connection established");
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
