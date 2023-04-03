package online.javaclass.bookstore.web.filter;

import online.javaclass.bookstore.service.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RestrictedCommandList {

    private static final Map<String, UserDto.Role> accessConfig = new HashMap<>();

    RestrictedCommandList() {
        accessConfig.put("logout", UserDto.Role.USER);
        accessConfig.put("/orders/confirm", UserDto.Role.USER);
        accessConfig.put("/books/add", UserDto.Role.MANAGER);
        accessConfig.put("/books/edit", UserDto.Role.MANAGER);
        accessConfig.put("/users/edit", UserDto.Role.ADMIN);
        accessConfig.put("/users/all", UserDto.Role.ADMIN);
        accessConfig.put("/orders/all", UserDto.Role.ADMIN);
    }

    private static UserDto.Role getRole(String command) {
        return accessConfig.get(command);
    }

    public static boolean isUserCommand(String command) {
        return getRole(command) == UserDto.Role.USER;
    }

    public static boolean isAdminCommand(String command) {
        return getRole(command) == UserDto.Role.ADMIN;
    }

    public static boolean isManagerCommand(String command) {
        return getRole(command) == UserDto.Role.MANAGER;
    }

    public static boolean requiresAuthentication(String command) {
        return (isUserCommand(command) || isManagerCommand(command) || isAdminCommand(command));
    }

    public static boolean requiresAuthorisation(String command) {
        return (isAdminCommand(command) || isManagerCommand(command));
    }
}

