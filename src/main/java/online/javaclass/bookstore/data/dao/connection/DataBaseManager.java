package online.javaclass.bookstore.data.dao.connection;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.sql.Connection;

@Log4j2
@Component
public class DataBaseManager implements AutoCloseable {
    private final String driver;
    private final String url;
    private final String user;
    private final String password;
    private ConnectionPool pool;

       private DataBaseManager(ConnectionPropertyManager propertyManager) {
        driver = propertyManager.getDriver();
        url = propertyManager.getUrl();
        user = propertyManager.getUser();
        password = propertyManager.getPassword();
    }

    public Connection getConnection() {
        if (pool == null) {
            pool = new ConnectionPool(driver, url, user, password);
            log.debug("Connection pool created");
        }
        log.debug("connection established");
        return pool.getConnection();
    }

    @Override
    public void close() {
        try {
            if (pool != null) {
                pool.destroyPool();
                pool = null;
                log.debug("Pool destroyed");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
