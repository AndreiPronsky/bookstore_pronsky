package online.javaclass.bookstore.web.controller.view;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.UserService;
import online.javaclass.bookstore.service.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class ChangeLangController {

    private final UserService service;

    @LogInvocation
    @RequestMapping("/change_lang")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String execute(HttpSession session, @RequestParam String lang) {
        UserDto userInSession = (UserDto) session.getAttribute("user");
        if (userInSession != null) {
            userInSession.setPreferredLocale(lang);
            service.save(userInSession);
        }
        session.setAttribute("lang", lang);
        return "index";
    }
}