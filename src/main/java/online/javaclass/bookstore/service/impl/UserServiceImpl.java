package online.javaclass.bookstore.service.impl;

import online.javaclass.bookstore.data.dao.UserDao;
import online.javaclass.bookstore.data.entities.User;
import online.javaclass.bookstore.service.exceptions.AppException;
import online.javaclass.bookstore.service.dto.UserDto;
import online.javaclass.bookstore.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDto create(UserDto userDto) {
        User user = toEntity(userDto);
        User created = userDao.create(user);
        return toDto(created);
    }

    @Override
    public UserDto update(UserDto userDto) {
        User user = toEntity(userDto);
        User updated = userDao.update(user);
        return toDto(updated);
    }

    @Override
    public UserDto getById(Long id) {
        User user = userDao.findById(id);
        if (user == null) throw new AppException("Unable to find user with id : " + id);
        return toDto(user);
    }

    @Override
    public UserDto getByEmail(String email) {
        User user = userDao.findByEmail(email);
        if (user == null) throw new AppException("Unable to find user with email : " + email);
        return toDto(user);
    }

    @Override
    public List<UserDto> getByLastName(String lastname) {
        List<UserDto> userDtos = userDao.findByLastName(lastname).stream()
                .map(this::toDto)
                .toList();
        if (userDtos.isEmpty()) throw new AppException("Unable to find users with lastname : " + lastname);
        return userDtos;
    }

    @Override
    public List<UserDto> getAll() {
        return userDao.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        boolean deleted = userDao.deleteById(id);
        if (!deleted) throw new AppException("Unable to delete user with id : " + id);
    }

    @Override
    public Long count() {
        return userDao.count();
    }

    private UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRole(user.getRole());
        userDto.setRating(user.getRating());
        return userDto;
    }

    private User toEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        user.setRating(userDto.getRating());
        return user;
    }
}
