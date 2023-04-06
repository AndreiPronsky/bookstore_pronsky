package online.javaclass.bookstore.web.filter;

import online.javaclass.bookstore.service.dto.UserDto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SecurityCheck {
    UserDto.Role[] allowed();

}
