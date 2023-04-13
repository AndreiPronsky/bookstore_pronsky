package online.javaclass.bookstore.data.converters.userConverters;

import online.javaclass.bookstore.data.entities.User;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RoleConverter implements AttributeConverter<User.Role, Integer> {
    @Override
    public Integer convertToDatabaseColumn(User.Role role) {
        Integer roleId = null;
        switch (role) {
            case USER -> roleId = 1;
            case ADMIN -> roleId = 2;
            case MANAGER -> roleId = 3;
        }
        return roleId;
    }

    @Override
    public User.Role convertToEntityAttribute(Integer roleId) {
        User.Role role = null;
        switch (roleId) {
            case 1 -> role = User.Role.USER;
            case 2 -> role = User.Role.ADMIN;
            case 3 -> role = User.Role.MANAGER;
        }
        return role;
    }
}
