package online.javaclass.bookstore;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Log4j2
@WebListener
public class AppContextListener implements ServletContextListener {
    private static AnnotationConfigApplicationContext context;

    public static AnnotationConfigApplicationContext getContext() {
        return context;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        log.debug("SERVLET CONTEXT LOADED");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        context.close();
        log.debug("SERVLET CONTEXT DESTROYED");
    }
}
