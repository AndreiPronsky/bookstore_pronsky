package online.javaclass.bookstore;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Log4j2
@Configuration
@ComponentScan
@PropertySource("classpath:/application.properties")
@PropertySource("classpath:/connection-config.properties")
public class AppConfig {

    @Value("${db.remote.url}")
    private String url;
    @Value("${db.remote.user}")
    private String username;
    @Value("${db.remote.password}")
    private String password;
    @Value("${db.driver}")
    private String driver;

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
        }

        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
