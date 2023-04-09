package online.javaclass.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.data.entities.User;
import online.javaclass.bookstore.data.repository.UserRepository;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.DigestService;
import online.javaclass.bookstore.service.EntityDtoMapper;
import online.javaclass.bookstore.service.UserService;
import online.javaclass.bookstore.service.dto.UserDto;
import online.javaclass.bookstore.service.dto.UserLoginDto;
import online.javaclass.bookstore.service.exceptions.LoginException;
import online.javaclass.bookstore.service.exceptions.UnableToFindException;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final EntityDtoMapper mapper;
    private final DigestService digest;

    @Override
    public Long count() {
        return userRepo.count();
    }


    @LogInvocation
    @Override
    public UserDto getById(Long id) {
        return mapper.toDto(userRepo.findById(id)
                .orElseThrow(() -> new UnableToFindException("user.unable_to_find_id")));
    }

    @LogInvocation
    @Override
    public UserDto login(UserLoginDto userLoginDto) {
        String email = userLoginDto.getEmail();
        String password = digest.hashPassword(userLoginDto.getPassword());
        return mapper.toDto(userRepo.login(email, password)
                .orElseThrow(() -> new LoginException("error.wrong_email_or_password")));
    }

    @LogInvocation
    @Override
    public List<UserDto> getByLastName(String lastname) {
        List<UserDto> users = userRepo.findByLastName(lastname)
                .stream()
                .map(mapper::toDto)
                .toList();
        if (users.isEmpty()) {
            throw new UnableToFindException("users.unable_to_find_lastname" + " " + lastname);
        }
        return users;
    }

    @LogInvocation
    @Override
    public List<UserDto> getAll() {
        List<UserDto> users = userRepo.findAll().stream()
                .map(mapper::toDto)
                .toList();
        if (users.isEmpty()) {
            throw new UnableToFindException("users.not_found");
        }
        return users;
    }

    @Override
    public UserDto save(UserDto userDto) {
        User user = mapper.toEntity(userDto);
        return mapper.toDto(userRepo.save(user));
    }

    @LogInvocation
    @Override
    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }
}
