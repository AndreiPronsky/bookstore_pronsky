package online.javaclass.bookstore.data.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class DataBaseManager implements AutoCloseable {
    private final ConnectionPool pool;
    final String PATH_TO_PROPS = "/connection-config.properties";
    final String url;
    final String user;
    final String password;
    private static final Logger log = LogManager.getLogger();

    public DataBaseManager() {
        Properties properties = new Properties();
        try (InputStream input = this.getClass().getResourceAsStream(PATH_TO_PROPS)) {
            properties.load(input);
        } catch (IOException e) {
            log.error("unable to extract connection properties");
        }

        url = properties.getProperty("db.url");
        user = properties.getProperty("db.user");
        password = properties.getProperty("db.password");
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
