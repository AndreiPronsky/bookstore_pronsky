package online.javaclass.bookstore.web.filter;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.service.dto.UserDto;
import online.javaclass.bookstore.web.exceptions.AuthorisationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthorisationFilter {
    private final HttpSession session;
    private final MessageSource messageSource;

    @Around(value = "@annotation(online.javaclass.bookstore.web.filter.SecurityCheck)")
    public Object checkAuthorisation(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        List<UserDto.Role> allowedRoles = getAllowedRoles(signature);
        UserDto.Role userRole = getUserRoleFromSession();
        if (allowedRoles.contains(userRole)) {
            return joinPoint.proceed(args);
        } else {
            throw new AuthorisationException(messageSource.getMessage("access_denied", new Object[]{}, LocaleContextHolder.getLocale()));
        }
    }

    private UserDto.Role getUserRoleFromSession() {
        UserDto userInSession = (UserDto) session.getAttribute("user");
        UserDto.Role userRole = null;
        if (userInSession != null) {
            userRole = userInSession.getRole();
        }
        return userRole;
    }

    private static List<UserDto.Role> getAllowedRoles(MethodSignature signature) {
        UserDto.Role[] allowedRoles = signature.getMethod()
                .getAnnotation(SecurityCheck.class)
                .allowed();
        return Arrays.asList(allowedRoles);
    }
}
