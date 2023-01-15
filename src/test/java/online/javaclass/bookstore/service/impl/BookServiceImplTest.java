package online.javaclass.bookstore.service.impl;

import online.javaclass.bookstore.MessageManager;
import online.javaclass.bookstore.data.entities.Book;
import online.javaclass.bookstore.data.repository.BookRepository;
import online.javaclass.bookstore.data.repository.impl.BookRepositoryImpl;
import online.javaclass.bookstore.exceptions.AppException;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.EntityDtoMapperService;
import online.javaclass.bookstore.service.dto.BookDto;
import online.javaclass.bookstore.service.dto.PageableDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {
    private static final long NOT_EXISTING_ID = 10000L;
    private static final long EXISTING_ID = 1L;
    private static final String EXISTING_ISBN = "3-0666-8990-3";
    private static final String NOT_EXISTING_ISBN = "1-1111-1111-1";
    private static final String EXISTING_PARTIAL_TITLE = "Pot";
    private static final String EXISTING_TITLE = "Harry Potter and the Half-Blood Prince";
    private static final String NOT_EXISTING_PARTIAL_TITLE = "123";
    private static final String NOT_EXISTING_TITLE = "Rick And Morty";
    private static final String EXISTING_PARTIAL_AUTHOR = "J.K";
    private static final String EXISTING_AUTHOR = "Noviolet Bulawayo";
    private static final String NOT_EXISTING_PARTIAL_AUTHOR = "Dostoyevsky";
    private static final String NOT_EXISTING_AUTHOR = "Aleksandr Sergeevich Pushkin";

    private static PageableDto pageableDto;
    private static EntityDtoMapperService mapper;
    private static MessageManager messageManager = MessageManager.INSTANCE;
    private static BookService bookService;
    private static BookRepository bookRepositoryMock;
    private static Book existingEntity;
    private static Book anotherExistingEntity;
    private static Book updatedEntity;
    private static Book createdEntity;
    private static Book notExistingEntity;
    private static BookDto expectedBookDto;
    private static BookDto notExistingDto;
    private static BookDto createdDto;
    private static BookDto updatedDto;
    private static List<Book> existingEntities;
    private static List<BookDto> expectedBookDtoList;


    @BeforeAll
    static void beforeAll() {
        messageManager.setLocale(Locale.getDefault().getLanguage());
        bookRepositoryMock = mock(BookRepositoryImpl.class);
        mapper = new EntityDtoMapperService();
        bookService = new BookServiceImpl(bookRepositoryMock, mapper);

        existingEntity = new Book();
        existingEntity.setId(EXISTING_ID);
        existingEntity.setTitle("Test title");
        existingEntity.setAuthor("Test author");
        existingEntity.setPrice(BigDecimal.TEN);
        existingEntity.setIsbn("0-0000-0000-0");
        existingEntity.setGenre(Book.Genre.ART);
        existingEntity.setCover(Book.Cover.HARD);
        existingEntity.setPages(100);
        existingEntity.setRating(BigDecimal.ONE);

        anotherExistingEntity = new Book();
        anotherExistingEntity.setId(2L);
        anotherExistingEntity.setTitle("Test title 2");
        anotherExistingEntity.setAuthor("Test author 2");
        anotherExistingEntity.setPrice(BigDecimal.valueOf(15L));
        anotherExistingEntity.setIsbn("4-4444-4444-4");
        anotherExistingEntity.setGenre(Book.Genre.FLORISTICS);
        anotherExistingEntity.setCover(Book.Cover.SPECIAL);
        anotherExistingEntity.setPages(500);
        anotherExistingEntity.setRating(BigDecimal.ONE);

        updatedEntity = new Book();
        updatedEntity.setId(EXISTING_ID);
        updatedEntity.setTitle("Test update title");
        updatedEntity.setAuthor("Test update author");
        updatedEntity.setPrice(BigDecimal.ONE);
        updatedEntity.setIsbn("2-2222-2222-2");
        updatedEntity.setGenre(Book.Genre.ENGINEERING);
        updatedEntity.setCover(Book.Cover.SPECIAL);
        updatedEntity.setPages(200);
        updatedEntity.setRating(BigDecimal.ONE);

        updatedDto = mapper.toDto(updatedEntity);

        expectedBookDto = new BookDto();
        expectedBookDto.setId(1L);
        expectedBookDto.setTitle("Test title");
        expectedBookDto.setAuthor("Test author");
        expectedBookDto.setPrice(BigDecimal.TEN);
        expectedBookDto.setIsbn("0-0000-0000-0");
        expectedBookDto.setGenre(BookDto.Genre.ART);
        expectedBookDto.setCover(BookDto.Cover.HARD);
        expectedBookDto.setPages(100);
        expectedBookDto.setRating(BigDecimal.ONE);

        notExistingEntity = new Book();
        notExistingEntity.setTitle("Test create");
        notExistingEntity.setAuthor("Test author for create");
        notExistingEntity.setPrice(BigDecimal.TEN);
        notExistingEntity.setIsbn("1-1111-1111-1");
        notExistingEntity.setGenre(Book.Genre.ART);
        notExistingEntity.setCover(Book.Cover.HARD);
        notExistingEntity.setRating(BigDecimal.ONE);
        notExistingEntity.setPages(100);

        notExistingDto = mapper.toDto(notExistingEntity);

        createdEntity = new Book();
        createdEntity.setId(30L);
        createdEntity.setTitle("Test create");
        createdEntity.setAuthor("Test author for create");
        createdEntity.setPrice(BigDecimal.TEN);
        createdEntity.setIsbn("1-1111-1111-1");
        createdEntity.setGenre(Book.Genre.ART);
        createdEntity.setCover(Book.Cover.HARD);
        createdEntity.setRating(BigDecimal.ONE);
        createdEntity.setPages(100);

        createdDto = mapper.toDto(createdEntity);

        existingEntities = new ArrayList<>();
        existingEntities.add(existingEntity);
        existingEntities.add(anotherExistingEntity);

        expectedBookDtoList = new ArrayList<>();
        expectedBookDtoList = existingEntities.stream().map(mapper::toDto).toList();

        pageableDto = new PageableDto(1, 5);
    }

    private static Stream<Arguments> provideValidSearchInput() {
        return Stream.of(Arguments.of(EXISTING_TITLE),
                Arguments.of(EXISTING_PARTIAL_TITLE),
                Arguments.of(EXISTING_AUTHOR),
                Arguments.of(EXISTING_PARTIAL_AUTHOR));
    }

    private static Stream<Arguments> provideInvalidSearchInput() {
        return Stream.of(Arguments.of(NOT_EXISTING_TITLE),
                Arguments.of(NOT_EXISTING_PARTIAL_TITLE),
                Arguments.of(NOT_EXISTING_AUTHOR),
                Arguments.of(NOT_EXISTING_PARTIAL_AUTHOR));
    }

    private static Stream<Arguments> provideValidAuthors() {
        return Stream.of(Arguments.of(EXISTING_AUTHOR),
                Arguments.of("J.K. Rowling"));
    }

    private static Stream<Arguments> provideInvalidAuthors() {
        return Stream.of(Arguments.of(NOT_EXISTING_AUTHOR),
                Arguments.of("Gorky"));
    }

    @BeforeEach
    void setUp() {
        reset(bookRepositoryMock);
    }

    @Test
    void createPositiveTest() {
        when(bookRepositoryMock.create(notExistingEntity)).thenReturn(createdEntity);
        BookDto created = bookService.create(notExistingDto);
        assertEquals(created, createdDto);
    }

    @Test
    void updatePositiveTest() {
        when(bookRepositoryMock.update(updatedEntity)).thenReturn(updatedEntity);
        BookDto updated = bookService.update(mapper.toDto(updatedEntity));
        assertEquals(updated, updatedDto);
    }

    @Test
    void updateNegativeTest() {
        when(bookRepositoryMock.update(notExistingEntity)).thenThrow(AppException.class);
        assertThrows(AppException.class, () -> bookService.update(notExistingDto));
    }

    @Test
    void getByIdPositiveTest() {
        when(bookRepositoryMock.getById(EXISTING_ID)).thenReturn(existingEntity);
        BookDto bookDto = bookService.getById(EXISTING_ID);
        assertEquals(expectedBookDto, bookDto);
    }

    @Test
    void getByIdNegativeTest() {
        when(bookRepositoryMock.getById(NOT_EXISTING_ID)).thenReturn(null);
        assertThrows(AppException.class, () -> bookService.getById(NOT_EXISTING_ID));
    }

    @Test
    void getByIsbnPositiveTest() {
        when(bookRepositoryMock.getByIsbn(EXISTING_ISBN)).thenReturn(existingEntity);
        BookDto bookDto = bookService.getByIsbn(EXISTING_ISBN);
        assertEquals(expectedBookDto, bookDto);
    }

    @Test
    void getByIsbnNegativeTest() {
        when(bookRepositoryMock.getByIsbn(NOT_EXISTING_ISBN)).thenReturn(null);
        assertThrows(AppException.class, () -> bookService.getByIsbn(NOT_EXISTING_ISBN));
    }

    @ParameterizedTest
    @MethodSource("provideValidSearchInput")
    void searchPositiveTest(String searchInput) {
        when(bookRepositoryMock.search(searchInput)).thenReturn(existingEntities);
        List<BookDto> bookDtoList = bookService.search(searchInput);
        assertEquals(bookDtoList, expectedBookDtoList);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidSearchInput")
    void searchNegativeTest(String searchInput) {
        when(bookRepositoryMock.search(searchInput)).thenReturn(new ArrayList<>());
        assertThrows(AppException.class, () -> bookService.search(searchInput));
    }

    @ParameterizedTest
    @MethodSource("provideValidAuthors")
    void getByAuthorPositiveTest(String searchInput) {
        when(bookRepositoryMock.search(searchInput)).thenReturn(existingEntities);
        List<BookDto> bookDtoList = bookService.search(searchInput);
        assertEquals(bookDtoList, expectedBookDtoList);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidAuthors")
    void getByAuthorNegativeTest(String searchInput) {
        when(bookRepositoryMock.search(searchInput)).thenReturn(new ArrayList<>());
        assertThrows(AppException.class, () -> bookService.search(searchInput));
    }

    @Test
    void getAllPositiveTest() {
        int limit = pageableDto.getLimit();
        int offset = pageableDto.getOffset();
        when(bookRepositoryMock.getAll(limit, offset)).thenReturn(existingEntities);
        List<BookDto> bookDtoList = bookService.getAll(pageableDto);
        assertEquals(bookDtoList, expectedBookDtoList);
    }

    @Test
    void getAllNegativeTest() {
        int limit = pageableDto.getLimit();
        int offset = pageableDto.getOffset();
        when(bookRepositoryMock.getAll(limit, offset)).thenReturn(new ArrayList<>());
        assertThrows(AppException.class, () -> bookService.getAll(pageableDto));
    }

    @Test
    void deleteByIdPositiveTest() {
        when(bookRepositoryMock.deleteById(EXISTING_ID)).thenReturn(true);
        bookService.deleteById(EXISTING_ID);
        verify(bookRepositoryMock).deleteById(EXISTING_ID);
    }

    @Test
    void deleteByIdNegativeTest() {
        when(bookRepositoryMock.deleteById(NOT_EXISTING_ID)).thenReturn(false);
        assertThrows(AppException.class, () -> bookService.deleteById(NOT_EXISTING_ID));
    }
}