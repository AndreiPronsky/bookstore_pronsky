package online.javaclass.bookstore.platform;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.ResourceBundle;

@Log4j2
@Component
public class MessageManager {
    private static final String BUNDLE_NAME = "messages";
    public static final ThreadLocal<ResourceBundle> context = new ThreadLocal<>();

    public MessageManager() {
    }

    public String getMessage(String key) {
        return context.get().getString(key);
    }

    public static void setLocale(String lang) {
        Locale locale = Locale.getDefault();
        if (lang != null) {
            locale = new Locale(lang);
        }
        context.set(ResourceBundle.getBundle(BUNDLE_NAME, locale));
    }
}
