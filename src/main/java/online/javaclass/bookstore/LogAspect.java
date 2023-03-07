package online.javaclass.bookstore;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Aspect
@Component
public class LogAspect {

    DateFormat df = new SimpleDateFormat("HH:mm:ss");

    @Before("@annotation(online.javaclass.bookstore.LogInvocation)")
    public void logMethodInvocation(JoinPoint jp) {
        Object[] args = jp.getArgs();
        Date date = new Date();
        System.out.println(df.format(date) + " [METHOD INVOCATION] " + jp.getTarget() + " "
                + jp.getSignature().getName() + " is called with args: " + Arrays.toString(args));
    }

    @AfterThrowing(value = "@annotation(online.javaclass.bookstore.LogInvocation)", throwing = "exception")
    public void logExceptions(JoinPoint jp, Exception exception) {
        Date date = new Date();
        System.out.println(df.format(date) + " [EXCEPTION OCCURRED] " + jp.getTarget() + " " + exception);
    }
}
