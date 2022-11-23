package online.javaclass.bookstore.data.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class ConnectionPool {
    private static final Logger log = LogManager.getLogger();
    public static final int POOL_SIZE = 16;
    private final BlockingDeque<ProxyConnection> freeConnections;

    ConnectionPool(String url, String user, String password) {

        freeConnections = new LinkedBlockingDeque<>(POOL_SIZE);
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
        try {
            return freeConnections.take();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public void releaseConnection(Connection connection) {
        if (connection instanceof ProxyConnection proxy ) {
            freeConnections.offer(proxy);
        } else {
            log.warn("Not proxy connection returned");
        }
    }

    public void destroyPool() {
        for (int i = 0; i < POOL_SIZE; i++) {
            freeConnections.remove();
        }
    }
}
