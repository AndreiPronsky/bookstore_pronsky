package online.javaclass.bookstore.data.repository.impl;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.data.EntityDtoMapperData;
import online.javaclass.bookstore.data.dao.UserDao;
import online.javaclass.bookstore.data.dto.UserDto;
import online.javaclass.bookstore.data.entities.User;
import online.javaclass.bookstore.data.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserDao userDao;
    private final EntityDtoMapperData mapper;

    @Override
    public User getById(Long id) {
        UserDto userDto = userDao.getById(id);
        return mapper.toEntity(userDto);
    }

    @Override
    public List<User> getAll(int limit, int offset) {
        List<UserDto> userDtos = userDao.getAll(limit, offset);
        List<User> users = new ArrayList<>();
        for (UserDto user : userDtos) {
            users.add(mapper.toEntity(user));
        }
        return users;
    }

    @Override
    public User create(User user) {
        UserDto userDto = mapper.toDto(user);
        UserDto createdUser = userDao.create(userDto);
        return mapper.toEntity(createdUser);
    }

    @Override
    public User update(User user) {
        UserDto userDto = mapper.toDto(user);
        UserDto updatedUser = userDao.update(userDto);
        return mapper.toEntity(updatedUser);
    }

    @Override
    public boolean deleteById(Long id) {
        return userDao.deleteById(id);
    }

    @Override
    public User getByEmail(String email) {
         if (userDao.getByEmail(email) == null) {
             return null;
         } else {
             return mapper.toEntity(userDao.getByEmail(email));
         }
    }

    @Override
    public List<User> getByLastName(String lastname, int limit, int offset) {
        List<UserDto> userDtos = userDao.getByLastName(lastname, limit, offset);
        List<User> users = new ArrayList<>();
        for (UserDto userDto : userDtos) {
            users.add(mapper.toEntity(userDto));
        }
        return users;
    }

    @Override
    public Long count() {
        return userDao.count();
    }
}
