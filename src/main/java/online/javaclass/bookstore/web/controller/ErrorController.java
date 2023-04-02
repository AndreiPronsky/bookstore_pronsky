package online.javaclass.bookstore.web.controller;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.service.exceptions.AppException;
import online.javaclass.bookstore.service.exceptions.LoginException;
import online.javaclass.bookstore.platform.MessageManager;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@ControllerAdvice(annotations = Controller.class)
public class ErrorController {

    private MessageManager messageManager;

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleLoginException(Model model, LoginException e) {
        model.addAttribute("message", e.getMessage());
        return "error";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleAppException(Model model, AppException e) {
        model.addAttribute("message", e.getMessage());
        return "error";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleServerException(Model model, RuntimeException e) {
        model.addAttribute("message", messageManager.getMessage("something_went_wrong"));
        return "error";
    }
}
