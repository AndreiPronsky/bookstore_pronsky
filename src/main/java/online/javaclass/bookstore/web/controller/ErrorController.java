package online.javaclass.bookstore.web.controller;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.service.exceptions.AppException;
import online.javaclass.bookstore.service.exceptions.LoginException;
import online.javaclass.bookstore.web.exceptions.AuthorisationException;
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
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public String handle(Model model, AppException e) {
        model.addAttribute("message", e.getMessage());
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
    public String handle(Model model, RuntimeException e) {
        model.addAttribute("message");
        return "error";
    }
}
