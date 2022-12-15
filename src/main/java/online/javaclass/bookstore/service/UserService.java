package online.javaclass.bookstore.service;

import online.javaclass.bookstore.controller.Pageable;
import online.javaclass.bookstore.service.dto.UserDto;

import java.util.List;

public interface UserService extends AbstractService<Long, UserDto> {

    UserDto getByEmail(String email);

    List<UserDto> getByLastName(String lastname);

    List<UserDto> getByLastName(String lastname, Pageable pageable);

    Long count();

    UserDto login(String email, String password);
}
