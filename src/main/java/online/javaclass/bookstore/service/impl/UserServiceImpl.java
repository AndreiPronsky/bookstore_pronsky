package online.javaclass.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.web.utils.PagingUtil;
import online.javaclass.bookstore.data.entities.User;
import online.javaclass.bookstore.data.repository.UserRepository;
import online.javaclass.bookstore.service.exceptions.LoginException;
import online.javaclass.bookstore.service.exceptions.UnableToCreateException;
import online.javaclass.bookstore.service.exceptions.UnableToFindException;
import online.javaclass.bookstore.service.exceptions.UnableToUpdateException;
import online.javaclass.bookstore.platform.MessageManager;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.DigestService;
import online.javaclass.bookstore.service.EntityDtoMapper;
import online.javaclass.bookstore.service.UserService;
import online.javaclass.bookstore.service.dto.PageableDto;
import online.javaclass.bookstore.service.dto.UserDto;
import online.javaclass.bookstore.service.dto.UserLoginDto;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final EntityDtoMapper mapper;
    private final DigestService digest;
    private final MessageManager messageManager;
    private final PagingUtil pagingUtil;

    @Override
    public Long count() {
        return userRepo.count();
    }

    @LogInvocation
    @Override
    public UserDto create(UserDto userDto) {
        userDto.setPassword(digest.hashPassword(userDto.getPassword()));
        User user = mapper.toEntity(userDto);
        try {
            User created = userRepo.create(user);
            return mapper.toDto(created);
        } catch (EntityExistsException e) {
            throw new UnableToCreateException(messageManager.getMessage("user.unable_to_create"));
        }
    }

    @LogInvocation
    @Override
    public UserDto update(UserDto userDto) {
        User user = mapper.toEntity(userDto);
        try {
            User updated = userRepo.update(user);
            return mapper.toDto(updated);
        } catch (IllegalArgumentException e) {
            throw new UnableToUpdateException(messageManager.getMessage("user.unable_to_update"));
        }
    }

    @LogInvocation
    @Override
    public UserDto getById(Long id) {
        User user = userRepo.findById(id);
        if (user == null) {
            throw new UnableToFindException(messageManager.getMessage("user.unable_to_find_id"));
        }
        return mapper.toDto(user);
    }

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
    public List<UserDto> getByLastName(String lastname, PageableDto pageable) {
        List<UserDto> users = userRepo.findByLastName(lastname, pageable.getLimit(), pageable.getOffset()).stream()
                .map(mapper::toDto)
                .toList();
        if (users.isEmpty()) {
            throw new UnableToFindException(messageManager.getMessage("users.unable_to_find_lastname" + " " + lastname));
        }
        return users;
    }

    @LogInvocation
    @Override
    public List<UserDto> getAll(PageableDto pageable) {
        Long totalItems = userRepo.count();
        Long totalPages = pagingUtil.getTotalPages(totalItems, pageable);
        pageable.setTotalItems(totalItems);
        pageable.setTotalPages(totalPages);
        List<UserDto> users = userRepo.findAll(pageable.getLimit(), pageable.getOffset()).stream()
                .map(mapper::toDto)
                .toList();
        if (users.isEmpty()) {
            throw new UnableToFindException(messageManager.getMessage("users.not_found"));
        }
        return users;
    }

    @LogInvocation
    @Override
    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }
}
