package online.javaclass.bookstore;

import lombok.extern.log4j.Log4j2;

import java.util.Locale;
import java.util.ResourceBundle;

@Log4j2
public enum MessageManager {
    INSTANCE;
    private static final String BUNDLE_NAME = "messages";
    public final ThreadLocal<ResourceBundle> context = new ThreadLocal<>();

    public String getMessage(String key) {
        return context.get().getString(key);
    }

    public void setLocale(String lang) {
        Locale locale = new Locale("ru");
        if (lang != null) {
            locale = new Locale(lang);
        }
        context.set(ResourceBundle.getBundle(BUNDLE_NAME, locale));
    }
}
