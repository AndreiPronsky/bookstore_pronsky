package online.javaclass.bookstore.service;

import online.javaclass.bookstore.service.dto.UserDto;
import online.javaclass.bookstore.service.dto.UserLoginDto;

import java.util.List;

public interface UserService extends AbstractService<Long, UserDto> {

    List<UserDto> getByLastName(String lastname);

    Long count();

    UserDto login(UserLoginDto userLoginDto);
}
