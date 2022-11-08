package online.javaclass.bookstore.service;

import online.javaclass.bookstore.service.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto create(UserDto userDto);

    UserDto update(UserDto userDto);

    UserDto getById(Long id);

    UserDto getByEmail(String email);

    List<UserDto> getByLastName(String lastname);

    List<UserDto> getAll();

    void deleteById(Long id);

    Long count();
}
