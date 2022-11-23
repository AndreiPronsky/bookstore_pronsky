package online.javaclass.bookstore.data.connection;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

@Log4j2
public class DataBaseManager implements AutoCloseable {
    public static final DataBaseManager INSTANCE = new DataBaseManager();
    private ConnectionPool pool;
    private static final String PATH_TO_PROPS = "/connection-config.properties";
    private final String url;
    private final String user;
    private final String password;

    private DataBaseManager() {
        Properties properties = new Properties();
        try (InputStream input = this.getClass().getResourceAsStream(PATH_TO_PROPS)) {
            properties.load(input);
        } catch (IOException e) {
            log.error("unable to extract connection properties", e);
        }

        url = properties.getProperty("db.url");
        user = properties.getProperty("db.user");
        password = properties.getProperty("db.password");
        pool = new ConnectionPool(url, user, password);
    }

    public Connection getConnection() {
        if (pool == null) {
            pool = new ConnectionPool(url, user, password);
            log.info("Connection pool created");
        }
        log.info("connection established");
        return pool.getConnection();
    }

    @Override
    public void close() {
        try {
            if (pool != null) {
                pool.destroyPool();
                pool = null;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
