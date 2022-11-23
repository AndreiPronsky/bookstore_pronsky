package online.javaclass.bookstore.data.connection;

import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Log4j2
public class ConnectionPool {
    private final BlockingDeque<ProxyConnection> freeConnections;
    private Queue<ProxyConnection> givenAwayConnections;
    public static final int POOL_SIZE = 8;

    ConnectionPool(String url, String user, String password) {

        freeConnections = new LinkedBlockingDeque<>(POOL_SIZE);
        givenAwayConnections = new ArrayDeque<>();
        try {
            Class.forName("org.postgresql.Driver");
            log.info("DB driver loaded");
            for (int i = 0; i < POOL_SIZE; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                freeConnections.offer(new ProxyConnection(connection, this));
                log.info("Connection granted");
            }
        } catch (ClassNotFoundException | SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    public Connection getConnection() {
        ProxyConnection connection;
        try {
           connection = freeConnections.take();
            givenAwayConnections.offer(connection);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection instanceof ProxyConnection) {
            givenAwayConnections.remove(connection);
            freeConnections.offer((ProxyConnection) connection);
        }
        else {
            log.error("Not proxy connection returned!!!");
            throw new RuntimeException();
        }
    }

    public void destroyPool() throws InterruptedException, SQLException {
        for (int i = 0; i < POOL_SIZE; i++) {
            freeConnections.take().reallyClose();
            deregisterDrivers();
        }
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                log.error("Unable to deregister driver " + driver);
            }
        });
    }
}
