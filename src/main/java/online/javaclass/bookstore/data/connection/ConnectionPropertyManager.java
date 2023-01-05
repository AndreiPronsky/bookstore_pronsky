package online.javaclass.bookstore.data.connection;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Log4j2
@Data
public class ConnectionPropertyManager {
    private final String url;
    private final String user;
    private final String password;
    private final String driver;

    public ConnectionPropertyManager(String PATH_TO_PROPS) {
        Properties properties = getProperties(PATH_TO_PROPS);
        driver = properties.getProperty("db.driver");

        if (properties.getProperty("db.connection.type").equals("local")) {
            url = properties.getProperty("db.local.url");
            user = properties.getProperty("db.local.user");
            password = properties.getProperty("db.local.password");
        } else {
            url = properties.getProperty("db.remote.url");
            user = properties.getProperty("db.remote.user");
            password = properties.getProperty("db.remote.password");
        }
    }

    private Properties getProperties(String PATH_TO_PROPS) {
        Properties properties = new Properties();
        try (InputStream input = this.getClass().getResourceAsStream(PATH_TO_PROPS)) {
            properties.load(input);
            log.debug("Properties extracted");
        } catch (IOException e) {
            log.error("unable to extract connection properties", e);
        }
        return properties;
    }
}
