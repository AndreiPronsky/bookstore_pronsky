package online.javaclass.bookstore;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.Locale;
import java.util.ResourceBundle;

@Log4j2
@Data
public class MessageManager {
    private static final String BUNDLE_NAME = "messages";
    private ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);

    public String getMessage(String key) {
        return resourceBundle.getString(key);
    }

    public void setLocale(String lang) {
        Locale locale = new Locale(lang);
        resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
    }
}
