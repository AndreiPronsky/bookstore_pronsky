package online.javaclass.bookstore.data.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.Map;

public class DataBaseManager implements AutoCloseable {
    private final ConnectionPool pool;
    private static final Logger log = LogManager.getLogger();

    public DataBaseManager() {
        ConnectionProperties properties = new ConnectionProperties();
        String PATH_TO_PROPS = "/connection-config.properties";
        final Map<String, String> props = properties.getConnectionProperties(PATH_TO_PROPS);
        String url = props.get("URL");
        String user = props.get("USER");
        String password = props.get("PASSWORD");
        pool = new ConnectionPool(url, user, password);
    }

    public Connection getConnection() {
        log.info("connection established");
        return pool.getConnection();
    }

    @Override
    public void close() {
        try {
            pool.destroyPool();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
