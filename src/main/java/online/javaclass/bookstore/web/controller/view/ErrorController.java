package online.javaclass.bookstore.web.controller.view;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.service.exceptions.AppException;
import online.javaclass.bookstore.service.exceptions.LoginException;
import online.javaclass.bookstore.web.exceptions.AuthorisationException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequiredArgsConstructor
@ControllerAdvice(annotations = Controller.class)
public class ErrorController {

    private final MessageSource messageSource;

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handle(Model model, LoginException e) {
        model.addAttribute("message", e.getMessage());
        return "error";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle(Model model, AppException e) {
        model.addAttribute("message",
                messageSource.getMessage("error.default_client", new Object[]{}, LocaleContextHolder.getLocale()));
        return "error";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handle(Model model, AuthorisationException e) {
        model.addAttribute("message", e.getMessage());
        return "error";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handle(Model model, Exception e) {
        model.addAttribute("message",
                messageSource.getMessage("error.default_server", new Object[]{}, LocaleContextHolder.getLocale()));
        return "error";
    }
}
