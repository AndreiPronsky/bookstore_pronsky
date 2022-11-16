package online.javaclass.bookstore.service.impl;

import online.javaclass.bookstore.data.dao.UserDao;
import online.javaclass.bookstore.data.entities.User;
import online.javaclass.bookstore.service.dto.UserDto;
import online.javaclass.bookstore.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private static final Logger log = LogManager.getLogger();

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDto create(UserDto userDto) {
        log.debug("create user");
        User user = toEntity(userDto);
        User created = userDao.create(user);
        return toDto(created);
    }

    @Override
    public UserDto update(UserDto userDto) {
        log.debug("update user");
        User user = toEntity(userDto);
        User updated = userDao.update(user);
        return toDto(updated);
    }

    @Override
    public UserDto getById(Long id) {
        log.debug("get user by id");
        User user = userDao.findById(id);
        return toDto(user);
    }

    @Override
    public UserDto getByEmail(String email) {
        log.debug("get user by email");
        User user = userDao.findByEmail(email);
        return toDto(user);
    }

    @Override
    public List<UserDto> getByLastName(String lastname) {
        log.debug("get user(s) by lastname");
        List<UserDto> userDtos = userDao.findByLastName(lastname).stream()
                .map(this::toDto)
                .toList();
        if (userDtos.isEmpty()) {
            log.error("Unable to find users with lastname : " + lastname);
        }
        return userDtos;
    }

    @Override
    public List<UserDto> getAll() {
        log.debug("get all users");
        return userDao.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        log.debug("delete user by id");
        boolean deleted = userDao.deleteById(id);
        if (!deleted) {
            log.error("Unable to delete user with id : " + id);
        }
    }

    @Override
    public Long count() {
        log.debug("count users");
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
