package online.javaclass.bookstore.data.dao;

import online.javaclass.bookstore.data.dto.UserDto;

import java.util.List;

public interface UserDao extends AbstractDao<Long, UserDto> {
    UserDto getByEmail(String email);

    List<UserDto> getByLastName(String lastname);

    List<UserDto> getByLastName(String lastname, int limit, int offset);

    Long count();
}
