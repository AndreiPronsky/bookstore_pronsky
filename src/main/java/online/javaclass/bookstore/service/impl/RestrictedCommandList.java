package online.javaclass.bookstore.service.impl;

import online.javaclass.bookstore.service.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RestrictedCommandList {

    private static final Map<String, UserDto.Role> accessConfig = new HashMap<>();

    RestrictedCommandList() {
        accessConfig.put("logout", UserDto.Role.USER);
        accessConfig.put("confirm_order", UserDto.Role.USER);
        accessConfig.put("add_book", UserDto.Role.MANAGER);
        accessConfig.put("add_book_form", UserDto.Role.MANAGER);
        accessConfig.put("edit_book", UserDto.Role.MANAGER);
        accessConfig.put("edit_book_form", UserDto.Role.MANAGER);
        accessConfig.put("edit_user", UserDto.Role.ADMIN);
        accessConfig.put("add_user", UserDto.Role.ADMIN);
        accessConfig.put("edit_user_form", UserDto.Role.ADMIN);
        accessConfig.put("user", UserDto.Role.ADMIN);
        accessConfig.put("users", UserDto.Role.ADMIN);
        accessConfig.put("orders", UserDto.Role.ADMIN);
        accessConfig.put("edit_order_admin", UserDto.Role.ADMIN);
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

