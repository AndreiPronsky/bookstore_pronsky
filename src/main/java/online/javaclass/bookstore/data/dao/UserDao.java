package online.javaclass.bookstore.data.dao;

import online.javaclass.bookstore.data.dto.UserDto;
import online.javaclass.bookstore.data.entities.User;

import java.util.List;

public interface UserDao extends AbstractDao<Long, UserDto>{
    UserDto findByEmail(String email);
    List<UserDto> findByLastName(String lastname);
    Long count();
}
