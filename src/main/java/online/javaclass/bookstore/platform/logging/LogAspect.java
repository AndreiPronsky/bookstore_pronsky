package online.javaclass.bookstore.platform.logging;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Arrays;

@Aspect
@Component
@Log4j2
public class LogAspect {

    @Before("@annotation(online.javaclass.bookstore.platform.logging.LogInvocation)")
    public void logMethodInvocation(JoinPoint jp) {
        Object[] args = jp.getArgs();
        log.debug(LocalTime.now() + " [METHOD INVOCATION] " + jp.getTarget() + " "
                + jp.getSignature().getName() + " is called with args: " + Arrays.toString(args));
    }

    @AfterThrowing(value = "@annotation(online.javaclass.bookstore.platform.logging.LogInvocation)", throwing = "exception")
    public void logExceptions(JoinPoint jp, Exception exception) {
        log.debug(LocalTime.now() + " [EXCEPTION OCCURRED] " + jp.getTarget() + " " + exception);
    }
}
