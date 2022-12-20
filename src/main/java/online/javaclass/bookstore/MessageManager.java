package online.javaclass.bookstore;

import lombok.extern.log4j.Log4j2;

import java.util.Locale;
import java.util.ResourceBundle;

@Log4j2
public enum MessageManager {
    INSTANCE;
    private static final String BUNDLE_NAME = "messages";
    private static final ThreadLocal<String> context = new ThreadLocal<>();
    private ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);

    public String getMessage(String key) {
        return resourceBundle.getString(key);
    }

    public void setLocale() {
        resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale(context.get()));
    }
}
