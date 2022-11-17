package online.javaclass.bookstore.data.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConnectionProperties {
    private static final Logger log = LogManager.getLogger();
    public Map<String, String> getConnectionProperties(String path) {
        Properties prop = new Properties();
        Map<String, String> properties = new HashMap<>();
        try (InputStream input = this.getClass().getResourceAsStream(path)) {
            prop.load(input);
            String url = prop.getProperty("URL");
            properties.put("URL", url);
            String user = prop.getProperty("USER");
            properties.put("USER", user);
            String password = prop.getProperty("PASSWORD");
            properties.put("PASSWORD", password);
            return properties;
        } catch (IOException e) {
            log.error("unable to extract connection properties");
        }
        return properties;
    }
}