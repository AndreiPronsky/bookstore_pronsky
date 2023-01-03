package online.javaclass.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.MessageManager;
import online.javaclass.bookstore.controller.PagingUtil;
import online.javaclass.bookstore.exceptions.ValidationException;
import online.javaclass.bookstore.service.dto.PageableDto;
import online.javaclass.bookstore.data.entities.User;
import online.javaclass.bookstore.data.repository.UserRepository;
import online.javaclass.bookstore.service.DigestService;
import online.javaclass.bookstore.service.EntityDtoMapperService;
import online.javaclass.bookstore.service.UserService;
import online.javaclass.bookstore.service.dto.UserDto;
import online.javaclass.bookstore.exceptions.LoginException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Log4j2
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final EntityDtoMapperService mapper;
    DigestService digest = new DigestServiceImpl();
    private final ThreadLocal<MessageManager> context = new ThreadLocal<>();
    MessageManager messageManager = context.get();

    @Override
    public UserDto login(String email, String password) {
        User user = userRepo.getByEmail(email);
        if (user == null || !user.getPassword().equals((digest.hashPassword(password)))) {
            throw new LoginException("Wrong email or password!");
        }
        return mapper.toDto(user);
    }

    @Override
    public UserDto create(UserDto userDto) {
        log.debug("create user");
        validate(userDto);
        userDto.setPassword(digest.hashPassword(userDto.getPassword()));
        User user = mapper.toEntity(userDto);
        User created = userRepo.create(user);
        return mapper.toDto(created);
    }

    @Override
    public UserDto update(UserDto userDto) {
        log.debug("update user");
        validate(userDto);
        User user = mapper.toEntity(userDto);
        User updated = userRepo.update(user);
        return mapper.toDto(updated);
    }

    @Override
    public UserDto getById(Long id) {
        log.debug("get user by id");
        User user = userRepo.getById(id);
        return mapper.toDto(user);
    }

    @Override
    public List<UserDto> getByLastName(String lastname) {
        log.debug("get user(s) by lastname");
        return userRepo.getByLastName(lastname).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<UserDto> getByLastName(String lastname, PageableDto pageable) {
        log.debug("get user(s) by lastname");
        return userRepo.getByLastName(lastname, pageable.getLimit(), pageable.getOffset()).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<UserDto> getAll() {
        log.debug("get all users");
        return userRepo.getAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<UserDto> getAll(PageableDto pageable) {
        log.debug("get all users");
        List<UserDto> users = userRepo.getAll(pageable.getLimit(), pageable.getOffset()).stream()
                .map(mapper::toDto)
                .toList();
        Long totalItems = userRepo.count();
        Long totalPages = PagingUtil.getTotalPages(totalItems, pageable);
        pageable.setTotalItems(userRepo.count());
        pageable.setTotalPages(totalPages);
        return users;
    }


    @Override
    public void deleteById(Long id) {
        log.debug("delete user by id");
        userRepo.deleteById(id);
    }

    @Override
    public Long count() {
        log.debug("count users");
        return userRepo.count();
    }

    @Override
    public void validate(UserDto user) {
        List<String> messages = new ArrayList<>();
        if (user.getFirstName() == null || user.getFirstName().isBlank()) {
            messages.add(messageManager.getMessage("error.invalid_firstname"));
        }
        if (user.getLastName() == null || user.getLastName().isBlank()) {
            messages.add(messageManager.getMessage("error.invalid_lastname"));
        }
        String validEmailRegex = "^[\\w\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (user.getEmail() == null || !user.getEmail().matches(validEmailRegex)) {
            messages.add(messageManager.getMessage("error.invalid_email"));
        }
        if (user.getPassword() == null || (user.getPassword().length() < 8)) {
            messages.add(messageManager.getMessage("error.invalid_password"));
        }
        if (user.getRating().compareTo(BigDecimal.ZERO) < 0 ||
                user.getRating().compareTo(BigDecimal.valueOf(5)) > 0) {
            messages.add(messageManager.getMessage("error.invalid rating"));
        }
        if (!messages.isEmpty()) {
            throw new ValidationException(messages);
        }
    }
}
