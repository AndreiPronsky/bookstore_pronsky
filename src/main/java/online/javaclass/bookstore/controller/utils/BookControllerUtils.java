package online.javaclass.bookstore.controller.utils;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//@Component
public class BookControllerUtils {
    private static final String PATH_TO_PROPS = "application.properties";
    private final String coverImageUploadDir;

    public String getCoverImageUploadDir() {
        return coverImageUploadDir;
    }

    public BookControllerUtils() {
        Properties properties = new Properties();
        try (InputStream input = this.getClass().getResourceAsStream(PATH_TO_PROPS)) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        coverImageUploadDir = properties.getProperty("cover.upload.dir");
    }
}
