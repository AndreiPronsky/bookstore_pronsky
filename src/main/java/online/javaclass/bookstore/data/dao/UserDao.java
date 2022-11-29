package online.javaclass.bookstore.data.dao;

import online.javaclass.bookstore.data.dto.UserDto;
import java.util.List;

public interface UserDao extends AbstractDao<Long, UserDto>{
    UserDto findByEmail(String email);
    List<UserDto> findByLastName(String lastname);
    Long count();
}
