package online.javaclass.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.data.entities.User;
import online.javaclass.bookstore.data.repository.UserRepository;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.DigestService;
import online.javaclass.bookstore.service.UserService;
import online.javaclass.bookstore.service.dto.UserDto;
import online.javaclass.bookstore.service.dto.UserLoginDto;
import online.javaclass.bookstore.service.exceptions.LoginException;
import online.javaclass.bookstore.service.exceptions.UnableToFindException;
import online.javaclass.bookstore.service.mapper.EntityDtoMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final EntityDtoMapper mapper;
    private final DigestService digest;
    private final MessageSource messageSource;

    @LogInvocation
    @Override
    public UserDto getById(Long id) {
        return mapper.toDto(userRepo.findById(id)
                .orElseThrow(() -> new UnableToFindException(getFailureMessage("user.unable_to_find_id"))));
    }

    @LogInvocation
    @Override
    public UserDto login(UserLoginDto userLoginDto) {
        String email = userLoginDto.getEmail();
        String password = digest.hashPassword(userLoginDto.getPassword());
        return mapper.toDto(userRepo.login(email, password)
                .orElseThrow(() -> new LoginException(getFailureMessage("error.wrong_email_or_password"))));
    }

    @LogInvocation
    @Override
    public Page<UserDto> getByLastName(Pageable pageable, String lastname) {
        Page<UserDto> users = userRepo.findByLastName(pageable, lastname).map(mapper::toDto);
        if (users.isEmpty()) {
            throw new UnableToFindException(getFailureMessage("users.unable_to_find_lastname") + " " + lastname);
        }
        return users;
    }

    @LogInvocation
    @Override
    public Page<UserDto> getAll(Pageable pageable) {
        Page<UserDto> users = userRepo.findAll(pageable).map(mapper::toDto);
        if (users.isEmpty()) {
            throw new UnableToFindException(getFailureMessage("users.not_found"));
        }
        return users;
    }

    @Override
    public UserDto save(UserDto userDto) {
        if (userDto.getId() == null) {
            userDto.setPassword(digest.hashPassword(userDto.getPassword()));
        }
        User user = mapper.toEntity(userDto);
        return mapper.toDto(userRepo.save(user));
    }

    private String getFailureMessage(String key) {
        return messageSource.getMessage(key, new Object[]{}, LocaleContextHolder.getLocale());
    }
}
