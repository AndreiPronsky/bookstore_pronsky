package online.javaclass.bookstore;

import java.util.Locale;
import java.util.ResourceBundle;

public enum MessageManager {
    INSTANCE;
    private static final String BUNDLE_NAME = "messages";
    public final ThreadLocal<ResourceBundle> context = new ThreadLocal<>();

    public String getMessage(String key) {
        return context.get().getString(key);
    }

    public void setLocale(String lang) {
        Locale locale = Locale.getDefault();
        if (lang != null) {
            locale = new Locale(lang);
        }
        context.set(ResourceBundle.getBundle(BUNDLE_NAME, locale));
    }
}
