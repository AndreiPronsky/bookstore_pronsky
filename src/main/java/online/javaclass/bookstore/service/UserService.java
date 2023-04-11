package online.javaclass.bookstore.service;

import online.javaclass.bookstore.service.dto.UserDto;
import online.javaclass.bookstore.service.dto.UserLoginDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService extends AbstractService<Long, UserDto> {

    Page<UserDto> getByLastName(Pageable pageable, String lastname);

    UserDto login(UserLoginDto userLoginDto);
}
