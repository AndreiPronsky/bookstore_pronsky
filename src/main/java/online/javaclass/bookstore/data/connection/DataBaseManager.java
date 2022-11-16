package online.javaclass.bookstore.data.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseManager implements AutoCloseable {
    private ProxyConnection connection;
    private static final Logger log = LogManager.getLogger();

    public DataBaseManager(String url, String user, String password) {
        try {
            this.connection = new ProxyConnection(DriverManager.getConnection(url, user, password));
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public Connection getConnection() {
        log.info("connection established");
        return connection;
    }

    @Override
    public void close() {
        try {
            connection.reallyClose();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
}
