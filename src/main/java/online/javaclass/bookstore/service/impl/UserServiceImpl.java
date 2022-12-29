package online.javaclass.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.controller.PagingUtil;
import online.javaclass.bookstore.service.dto.PageableDto;
import online.javaclass.bookstore.data.entities.User;
import online.javaclass.bookstore.data.repository.UserRepository;
import online.javaclass.bookstore.service.DigestService;
import online.javaclass.bookstore.service.EntityDtoMapperService;
import online.javaclass.bookstore.service.UserService;
import online.javaclass.bookstore.service.dto.UserDto;
import online.javaclass.bookstore.service.exceptions.LoginException;

import java.util.List;


@Log4j2
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final EntityDtoMapperService mapper;
    DigestService digest = new DigestServiceImpl();

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
        userDto.setPassword(digest.hashPassword(userDto.getPassword()));
        User user = mapper.toEntity(userDto);
        User created = userRepo.create(user);
        return mapper.toDto(created);
    }

    @Override
    public UserDto update(UserDto userDto) {
        log.debug("update user");
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
}
