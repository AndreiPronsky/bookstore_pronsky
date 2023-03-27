package online.javaclass.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.utils.PagingUtil;
import online.javaclass.bookstore.data.entities.User;
import online.javaclass.bookstore.data.repository.UserRepository;
import online.javaclass.bookstore.exceptions.LoginException;
import online.javaclass.bookstore.exceptions.UnableToDeleteException;
import online.javaclass.bookstore.exceptions.UnableToFindException;
import online.javaclass.bookstore.platform.MessageManager;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.DigestService;
import online.javaclass.bookstore.service.EntityDtoMapperService;
import online.javaclass.bookstore.service.UserService;
import online.javaclass.bookstore.service.dto.PageableDto;
import online.javaclass.bookstore.service.dto.UserDto;
import online.javaclass.bookstore.service.dto.UserLoginDto;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final EntityDtoMapperService mapper;
    private final DigestService digest;
    private final MessageManager messageManager;
    private final PagingUtil pagingUtil;

    @LogInvocation
    @Override
    public UserDto login(UserLoginDto userLoginDto) {
        User user = userRepo.findByEmail(userLoginDto.getEmail());
        if (user == null || !user.getPassword().equals((digest.hashPassword(userLoginDto.getPassword())))) {
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
        User user = userRepo.findById(id);
        if (user == null) {
            throw new UnableToFindException(messageManager.getMessage("user.unable_to_find_id"));
        } else {
            return mapper.toDto(user);
        }
    }

    @LogInvocation
    @Override
    public List<UserDto> getByLastName(String lastname, PageableDto pageable) {
        List<UserDto> users = userRepo.findByLastName(lastname, pageable.getLimit(), pageable.getOffset()).stream()
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
        List<UserDto> users = userRepo.findAll(pageable.getLimit(), pageable.getOffset()).stream()
                .map(mapper::toDto)
                .toList();
        if (users.isEmpty()) {
            throw new UnableToFindException(messageManager.getMessage("users.not_found"));
        } else {
            Long totalItems = userRepo.count();
            Long totalPages = pagingUtil.getTotalPages(totalItems, pageable);
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
