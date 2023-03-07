package online.javaclass.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.LogInvocation;
import online.javaclass.bookstore.MessageManager;
import online.javaclass.bookstore.controller.PagingUtil;
import online.javaclass.bookstore.data.entities.User;
import online.javaclass.bookstore.data.repository.UserRepository;
import online.javaclass.bookstore.exceptions.LoginException;
import online.javaclass.bookstore.exceptions.UnableToDeleteException;
import online.javaclass.bookstore.exceptions.UnableToFindException;
import online.javaclass.bookstore.service.DigestService;
import online.javaclass.bookstore.service.EntityDtoMapperService;
import online.javaclass.bookstore.service.UserService;
import online.javaclass.bookstore.service.dto.PageableDto;
import online.javaclass.bookstore.service.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final EntityDtoMapperService mapper;
    DigestService digest = new DigestServiceImpl();
    private final MessageManager messageManager;

    @LogInvocation
    @Override
    public UserDto login(String email, String password) {
        User user = userRepo.getByEmail(email);
        if (user == null || !user.getPassword().equals((digest.hashPassword(password)))) {
            throw new LoginException(messageManager.getMessage("error.wrong_email_or_password"));
        }
        return mapper.toDto(user);
    }

    @LogInvocation
    @Override
    public UserDto create(UserDto userDto) {
        userDto.setPassword(digest.hashPassword(userDto.getPassword()));
        User user = mapper.toEntity(userDto);
        User created = userRepo.create(user);
        return mapper.toDto(created);
    }

    @LogInvocation
    @Override
    public UserDto update(UserDto userDto) {
        User user = mapper.toEntity(userDto);
        User updated = userRepo.update(user);
        return mapper.toDto(updated);
    }

    @LogInvocation
    @Override
    public UserDto getById(Long id) {
        User user = userRepo.getById(id);
        if (user == null) {
            throw new UnableToFindException(messageManager.getMessage("user.unable_to_find_id"));
        } else {
            return mapper.toDto(user);
        }
    }

    @LogInvocation
    @Override
    public List<UserDto> getByLastName(String lastname, PageableDto pageable) {
        List<UserDto> users = userRepo.getByLastName(lastname, pageable.getLimit(), pageable.getOffset()).stream()
                .map(mapper::toDto)
                .toList();
        if (users.isEmpty()) {
            throw new UnableToFindException(messageManager.getMessage("users.unable_to_find_lastname" + " " + lastname));
        } else {
            return users;
        }
    }

    @LogInvocation
    @Override
    public List<UserDto> getAll(PageableDto pageable) {
        List<UserDto> users = userRepo.getAll(pageable.getLimit(), pageable.getOffset()).stream()
                .map(mapper::toDto)
                .toList();
        if (users.isEmpty()) {
            throw new UnableToFindException(messageManager.getMessage("users.not_found"));
        } else {
            Long totalItems = userRepo.count();
            Long totalPages = PagingUtil.getTotalPages(totalItems, pageable);
            pageable.setTotalItems(userRepo.count());
            pageable.setTotalPages(totalPages);
            return users;
        }
    }

    @LogInvocation
    @Override
    public void deleteById(Long id) {
        boolean deleted = userRepo.deleteById(id);
        if (!deleted) {
            throw new UnableToDeleteException(messageManager.getMessage("user.unable_to_delete_id") + " " + id);
        }
    }

    @Override
    public Long count() {
        return userRepo.count();
    }
}
