package online.javaclass.bookstore.service;

import online.javaclass.bookstore.service.dto.UserDto;

import java.util.List;

public interface UserService extends AbstractService<Long, UserDto> {

    List<UserDto> getByLastName(String lastname);

    UserDto login(String email, String password);
}
