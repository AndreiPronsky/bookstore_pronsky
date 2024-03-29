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
import online.javaclass.bookstore.service.mapper.Mapper;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final Mapper mapper;
    private final DigestService digest;
    private final MessageSource messageSource;

    @LogInvocation
    @Override
    public UserDto getById(Long id) {
        return userRepo.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new UnableToFindException(getFailureMessage("user.unable_to_find_id", id)));
    }

    @LogInvocation
    @Override
    public UserDto login(UserLoginDto userLoginDto) {
        String email = userLoginDto.getEmail().toLowerCase();
        String password = digest.hashPassword(userLoginDto.getPassword());
        return userRepo.login(email, password)
                .map(mapper::toDto)
                .orElseThrow(() -> new LoginException(getFailureMessage("error.wrong_email_or_password")));
    }

    @LogInvocation
    @Override
    public Page<UserDto> getByLastName(Pageable pageable, String lastname) {
        return userRepo.findByLastName(pageable, lastname)
                .map(mapper::toDto);
    }

    @LogInvocation
    @Override
    public Page<UserDto> getAll(Pageable pageable) {
        return userRepo.findAll(pageable)
                .map(mapper::toDto);
    }

    @Override
    public UserDto save(UserDto userDto) {
        if (userDto.getId() == null) {
            userDto.setPassword(digest.hashPassword(userDto.getPassword()));
        }
        userDto.setEmail(userDto.getEmail().toLowerCase());
        User user = mapper.toEntity(userDto);
        return mapper.toDto(userRepo.save(user));
    }

    private String getFailureMessage(String key, Object... objects) {
        return messageSource.getMessage(key, objects, LocaleContextHolder.getLocale());
    }
}
