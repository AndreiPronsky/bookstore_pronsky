package online.javaclass.bookstore;

import online.javaclass.bookstore.web.filter.AuthenticationFilter;
import online.javaclass.bookstore.web.filter.AuthorisationFilter;
import online.javaclass.bookstore.web.interceptor.LogInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Application implements WebMvcConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("css/**", "coverImages/**", "serviceImages/**")
                .addResourceLocations("classpath:/static/css/", "classpath:/static/serviceImages/",
                        "classpath:/static/coverImages/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public LogInterceptor logInterceptor() {
        return new LogInterceptor();
    }

//    @Bean
//    public FilterRegistrationBean<AuthenticationFilter> authenticationFilter() {
//        FilterRegistrationBean<AuthenticationFilter> filterRegistrationBean = new FilterRegistrationBean<>();
//        filterRegistrationBean.setFilter(new AuthenticationFilter());
//        filterRegistrationBean.addUrlPatterns("/users/all", "/users/edit", "/users/${id}", "/users/logout",
//                "/users/my_orders", "/books/add", "/books/edit", "/cart/edit", "/orders/confirm",
//                "/orders/edit", "/orders/${id}", "/orders/all");
//        filterRegistrationBean.setOrder(1);
//        return filterRegistrationBean;
//    }

//    @Bean
//    public FilterRegistrationBean<AuthorisationFilter> authorisationFilter() {
//        FilterRegistrationBean<AuthorisationFilter> filterRegistrationBean = new FilterRegistrationBean<>();
//        filterRegistrationBean.setFilter(new AuthorisationFilter());
//        filterRegistrationBean.set
//    }
}
