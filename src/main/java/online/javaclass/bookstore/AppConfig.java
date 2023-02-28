package online.javaclass.bookstore;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@ComponentScan
@PropertySource("classpath:/application.properties")
@PropertySource("classpath:/connection-config.properties")
public class AppConfig {
}
