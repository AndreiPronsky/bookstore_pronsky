package online.javaclass.bookstore.service.impl;

import online.javaclass.bookstore.service.dto.UserDto;

import java.util.HashMap;
import java.util.Map;

public enum RestrictedCommandList {
    INSTANCE;

    private final Map<String, UserDto.Role> accessConfig = new HashMap<>();

    RestrictedCommandList() {
        accessConfig.put("add_book", UserDto.Role.ADMIN);
        accessConfig.put("add_user", UserDto.Role.ADMIN);
        accessConfig.put("edit_book", UserDto.Role.ADMIN);
        accessConfig.put("edit_user", UserDto.Role.ADMIN);
        accessConfig.put("logout", UserDto.Role.USER);
        accessConfig.put("user", UserDto.Role.ADMIN);
        accessConfig.put("users", UserDto.Role.ADMIN);
    }

    private UserDto.Role getRole(String command) {
        return accessConfig.get(command);
    }

    public boolean isUserCommand(String command) {
        return getRole(command) == UserDto.Role.USER;
    }

    public boolean isAdminCommand(String command) {
        return getRole(command) == UserDto.Role.ADMIN;
    }
}