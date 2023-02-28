//package online.javaclass.bookstore.service.impl;
//
//import online.javaclass.bookstore.MessageManager;
//import online.javaclass.bookstore.data.entities.User;
//import online.javaclass.bookstore.data.repository.UserRepository;
//import online.javaclass.bookstore.data.repository.impl.UserRepositoryImpl;
//import online.javaclass.bookstore.exceptions.AppException;
//import online.javaclass.bookstore.service.DigestService;
//import online.javaclass.bookstore.service.EntityDtoMapperService;
//import online.javaclass.bookstore.service.UserService;
//import online.javaclass.bookstore.service.dto.PageableDto;
//import online.javaclass.bookstore.service.dto.UserDto;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//import java.util.stream.Stream;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//class UserServiceImplTest {
//
//    private static final long EXISTING_ID = 11L;
//    private static final long NOT_EXISTING_ID = 10000L;
//    private static final String EXISTING_EMAIL = "user@mail.com";
//    private static final String EXISTING_PASSWORD = "user";
//    private static final String NOT_EXISTING_EMAIL = "notexisting@mail.com";
//    private static final String EXISTING_LASTNAME = "Jolie";
//    private static final String NOT_EXISTING_LASTNAME = "Lastname";
//    private static PageableDto pageableDto;
//    private static EntityDtoMapperService mapper;
//    private static MessageManager messageManager = MessageManager.INSTANCE;
//    private static UserService userService;
//    private static UserRepository userRepositoryMock;
//    private static DigestService digest = new DigestServiceImpl();
//    private static User existingEntity;
//    private static User anotherExistingEntity;
//    private static User notExistingEntity;
//    private static User expectedEntity;
//    private static User createdEntity;
//    private static List<User> existingEntities;
//    private static UserDto existingDto;
//    private static UserDto anotherExistingDto;
//    private static UserDto notExistingDto;
//    private static UserDto expectedDto;
//    private static UserDto createdDto;
//    private static List<UserDto> existingDtoList;
//
//    @BeforeAll
//    static void beforeAll() {
//        messageManager.setLocale(Locale.getDefault().getLanguage());
//        userRepositoryMock = mock(UserRepositoryImpl.class);
//        mapper = new EntityDtoMapperService();
//        userService = new UserServiceImpl(userRepositoryMock, mapper);
//        pageableDto = new PageableDto(1, 5);
//
//        existingEntity = new User();
//        existingEntity.setId(EXISTING_ID);
//        existingEntity.setFirstName("User");
//        existingEntity.setLastName("User");
//        existingEntity.setEmail("user@mail.com");
//        existingEntity.setPassword(digest.hashPassword("user"));
//        existingEntity.setRating(null);
//        existingEntity.setRole(User.Role.USER);
//
//        existingDto = mapper.toDto(existingEntity);
//
//        anotherExistingEntity = new User();
//        anotherExistingEntity.setId(2L);
//        anotherExistingEntity.setFirstName("Name");
//        anotherExistingEntity.setLastName("Surname");
//        anotherExistingEntity.setEmail("another@mail.by");
//        anotherExistingEntity.setPassword(digest.hashPassword("other_password"));
//        anotherExistingEntity.setRating(new BigDecimal(4));
//        anotherExistingEntity.setRole(User.Role.MANAGER);
//
//        anotherExistingDto = mapper.toDto(anotherExistingEntity);
//
//        notExistingEntity = new User();
//        notExistingEntity.setFirstName("Not");
//        notExistingEntity.setLastName("Existing");
//        notExistingEntity.setEmail(NOT_EXISTING_EMAIL);
//        notExistingEntity.setPassword("doesntMatter");
//        notExistingEntity.setRating(BigDecimal.ONE);
//        notExistingEntity.setRole(User.Role.USER);
//
//        notExistingDto = mapper.toDto(notExistingEntity);
//
//        expectedEntity = new User();
//        expectedEntity.setId(30L);
//        expectedEntity.setFirstName("Not");
//        expectedEntity.setLastName("Existing");
//        expectedEntity.setEmail(NOT_EXISTING_EMAIL);
//        expectedEntity.setPassword("doesntMatter");
//        expectedEntity.setRating(BigDecimal.ONE);
//        expectedEntity.setRole(User.Role.USER);
//
//        expectedDto = mapper.toDto(expectedEntity);
//
//        createdEntity = new User();
//        createdEntity.setId(50L);
//        createdEntity.setFirstName("Not");
//        createdEntity.setLastName("Existing");
//        createdEntity.setEmail(NOT_EXISTING_EMAIL);
//        createdEntity.setPassword("doesntMatter");
//        createdEntity.setRating(BigDecimal.ONE);
//        createdEntity.setRole(User.Role.USER);
//
//        createdDto = mapper.toDto(createdEntity);
//
//        existingEntities = new ArrayList<>();
//        existingEntities.add(existingEntity);
//        existingEntities.add(anotherExistingEntity);
//
//        existingDtoList = new ArrayList<>();
//        existingDtoList.add(existingDto);
//        existingDtoList.add(anotherExistingDto);
//
//
//    }
//
//    private static Stream<Arguments> provideInvalidLoginInput() {
//        return Stream.of(Arguments.of("users", "password"),
//                Arguments.of("wrong", "email"),
//                Arguments.of("wrong", "password"),
//                Arguments.of("user@mail.com", "wrongPassword"),
//                Arguments.of("wrong@email.com", "user"),
//                Arguments.of("", "user"),
//                Arguments.of("user@mail.com", ""),
//                Arguments.of(null, "user"),
//                Arguments.of("user@mail.com", null));
//    }
//
//    @BeforeEach
//    void setUp() {
//        reset(userRepositoryMock);
//    }
//
//    @Test
//    void loginPositiveTest() {
//        when(userRepositoryMock.getByEmail(EXISTING_EMAIL)).thenReturn(existingEntity);
//        UserDto userDto = userService.login(EXISTING_EMAIL, EXISTING_PASSWORD);
//        assertEquals(userDto, existingDto);
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideInvalidLoginInput")
//    void loginNegativeTest(String email, String password) {
//        when(userRepositoryMock.getByEmail(email)).thenReturn(null);
//        assertThrows(AppException.class, () -> userService.login(email, password));
//    }
//
//    @Test
//    void createPositiveTest() {
//        when(userRepositoryMock.create(notExistingEntity)).thenReturn(createdEntity);
//        UserDto created = userService.create(notExistingDto);
//        assertEquals(created, createdDto);
//    }
//
//    @Test
//    void updatePositiveTest() {
//        when(userRepositoryMock.update(existingEntity)).thenReturn(expectedEntity);
//        UserDto user = userService.update(existingDto);
//        assertEquals(user, expectedDto);
//    }
//
//    @Test
//    void updateNegativeTest() {
//        when(userRepositoryMock.update(notExistingEntity)).thenThrow(AppException.class);
//        assertThrows(AppException.class, () -> userService.update(notExistingDto));
//    }
//
//    @Test
//    void getByIdPositiveTest() {
//        when(userRepositoryMock.getById(EXISTING_ID)).thenReturn(existingEntity);
//        UserDto user = userService.getById(EXISTING_ID);
//        assertEquals(user, existingDto);
//    }
//
//    @Test
//    void getByIdNegativeTest() {
//        when(userRepositoryMock.getById(NOT_EXISTING_ID)).thenReturn(null);
//        assertThrows(AppException.class, () -> userService.getById(NOT_EXISTING_ID));
//    }
//
//    @Test
//    void getByLastNamePositiveTest() {
//        when(userRepositoryMock.getByLastName(EXISTING_LASTNAME, pageableDto.getLimit(), pageableDto.getOffset()))
//                .thenReturn(existingEntities);
//        List<UserDto> userDtoList = userService.getByLastName(EXISTING_LASTNAME, pageableDto);
//        assertEquals(userDtoList, existingDtoList);
//    }
//
//    @Test
//    void getByLastNameNegativeTest() {
//        when(userRepositoryMock.getByLastName(NOT_EXISTING_LASTNAME, pageableDto.getLimit(), pageableDto.getOffset()))
//                .thenThrow(AppException.class);
//        assertThrows(AppException.class, () -> userService.getByLastName(NOT_EXISTING_LASTNAME, pageableDto));
//    }
//
//    @Test
//    void getAllPositiveTest() {
//        when(userRepositoryMock.getAll(pageableDto.getLimit(), pageableDto.getOffset())).thenReturn(existingEntities);
//        List<UserDto> userDtoList = userService.getAll(pageableDto);
//        assertEquals(userDtoList, existingDtoList);
//    }
//
//    @Test
//    void getAllNegativeTest() {
//        when(userRepositoryMock.getAll(pageableDto.getLimit(), pageableDto.getOffset())).thenReturn(new ArrayList<>());
//        assertThrows(AppException.class, () -> userService.getAll(pageableDto));
//    }
//
//    @Test
//    void deleteByIdPositiveTest() {
//        when(userRepositoryMock.deleteById(EXISTING_ID)).thenReturn(true);
//        userService.deleteById(EXISTING_ID);
//        verify(userRepositoryMock).deleteById(EXISTING_ID);
//    }
//
//    @Test
//    void deleteByIdNegativeTest() {
//        when(userRepositoryMock.deleteById(NOT_EXISTING_ID)).thenReturn(false);
//        assertThrows(AppException.class, () -> userService.deleteById(NOT_EXISTING_ID));
//    }
//}