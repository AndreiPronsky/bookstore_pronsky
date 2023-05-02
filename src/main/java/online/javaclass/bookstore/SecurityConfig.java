package online.javaclass.bookstore;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity

//                Session security configuration

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .maximumSessions(2)
                .maxSessionsPreventsLogin(true)
                .and()
                .and()

//                CSRF

                .csrf().disable()

//                CORS

                .cors().disable()

//                Authentication/Authorisation filtration

                .authorizeRequests()
                .mvcMatchers("/css/**", "/serviceImages/**", "/coverImages/**", "/", "/books/all", "/cart",
                        "/users/login", "/change_lang", "/users/register", "/")
                .permitAll()
                .mvcMatchers("/orders/confirm", "/orders/edit", "/orders/edit", "/orders/all/{id}")
                .hasAuthority("USER")
                .mvcMatchers("/books/add", "/books/edit", "/books/delete")
                .hasAuthority("MANAGER")
                .mvcMatchers("/users/add", "/users/edit", "/users/delete", "/users/all",
                        "orders/all", "/orders/edit")
                .hasAuthority("ADMIN")
                .anyRequest()
                .authenticated()
                .and()

//                Form Authentication (email & password)

                .formLogin()
                .loginPage("/users/login")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()

                .build();
    }

    @Bean
    public HttpSessionEventPublisher sessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public UserDetailsService detailsService(DataSource dataSource) {
        JdbcUserDetailsManager detailsManager = new JdbcUserDetailsManager(dataSource);
        detailsManager.setUsersByUsernameQuery("SELECT email, password, active FROM users WHERE email = ?");
        detailsManager.setAuthoritiesByUsernameQuery(
                "SELECT u.email, r.name FROM users u JOIN roles r ON u.role_id = r.id WHERE email = ?");
        return detailsManager;
    }

    @Bean
    public PasswordEncoder encoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
