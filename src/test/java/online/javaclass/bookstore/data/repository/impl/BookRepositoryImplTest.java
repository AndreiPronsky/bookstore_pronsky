//package online.javaclass.bookstore.data.repository.impl;
//
//import online.javaclass.bookstore.data.dao.BookDao;
//import online.javaclass.bookstore.data.dao.impl.BookDaoImpl;
//import online.javaclass.bookstore.data.entities.Book;
//import online.javaclass.bookstore.data.repository.BookRepository;
//import online.javaclass.bookstore.exceptions.AppException;
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
//import java.util.stream.Stream;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class BookRepositoryImplTest {
//
//    private static final long NOT_EXISTING_ID = 10000L;
//    private static final long EXISTING_ID = 1L;
//    private static final String EXISTING_ISBN = "3-0666-8990-3";
//    private static final String NOT_EXISTING_ISBN = "1-1111-1111-1";
//    private static final String EXISTING_PARTIAL_TITLE = "Pot";
//    private static final String EXISTING_TITLE = "Harry Potter and the Half-Blood Prince";
//    private static final String NOT_EXISTING_PARTIAL_TITLE = "123";
//    private static final String NOT_EXISTING_TITLE = "Rick And Morty";
//    private static final String EXISTING_PARTIAL_AUTHOR = "J.K";
//    private static final String EXISTING_AUTHOR = "Noviolet Bulawayo";
//    private static final String NOT_EXISTING_PARTIAL_AUTHOR = "Dostoyevsky";
//    private static final String NOT_EXISTING_AUTHOR = "Aleksandr Sergeevich Pushkin";
//    private static final int LIMIT = 1;
//    private static final int OFFSET = 0;
//
//    private static BookRepository bookRepo;
//    private static BookDao bookDaoMock;
//    private static Book existingEntity;
//    private static Book anotherExistingEntity;
//    private static Book notExistingEntity;
//    private static Book expectedEntity;
//    private static Book entityForCreation;
//    private static List<Book> existingEntities;
//
//    @BeforeAll
//    static void beforeAll() {
//        bookDaoMock = mock(BookDaoImpl.class);
//        bookRepo = new BookRepositoryImpl(bookDaoMock);
//
//        existingEntity = new Book();
//        existingEntity.setId(EXISTING_ID);
//        existingEntity.setTitle("Existing title");
//        existingEntity.setAuthor("Existing author");
//        existingEntity.setPrice(BigDecimal.TEN);
//        existingEntity.setIsbn("0-0000-0000-0");
//        existingEntity.setGenre(Book.Genre.ART);
//        existingEntity.setCover(Book.Cover.HARD);
//        existingEntity.setPages(100);
//        existingEntity.setRating(BigDecimal.ONE);
//
//        anotherExistingEntity = new Book();
//        anotherExistingEntity.setId(2L);
//        anotherExistingEntity.setTitle("Existing title 2");
//        anotherExistingEntity.setAuthor("Another existing Author");
//        anotherExistingEntity.setPrice(BigDecimal.TEN);
//        anotherExistingEntity.setIsbn("1-1111-1111-1");
//        anotherExistingEntity.setGenre(Book.Genre.FICTION);
//        anotherExistingEntity.setCover(Book.Cover.SOFT);
//        anotherExistingEntity.setPages(300);
//        anotherExistingEntity.setRating(BigDecimal.valueOf(4.5));
//
//        existingEntities.add(existingEntity);
//        existingEntities.add(anotherExistingEntity);
//
//        expectedEntity = new Book();
//        expectedEntity.setId(30L);
//        expectedEntity.setTitle("Existing title");
//        expectedEntity.setAuthor("Existing author");
//        expectedEntity.setPrice(BigDecimal.TEN);
//        expectedEntity.setIsbn("0-0000-0000-0");
//        expectedEntity.setGenre(Book.Genre.ART);
//        expectedEntity.setCover(Book.Cover.HARD);
//        expectedEntity.setPages(100);
//        expectedEntity.setRating(BigDecimal.ONE);
//
//        notExistingEntity = new Book();
//        notExistingEntity.setId(NOT_EXISTING_ID);
//        notExistingEntity.setTitle("Not Existing title");
//        notExistingEntity.setAuthor("Not Existing Author");
//        notExistingEntity.setPrice(BigDecimal.TEN);
//        notExistingEntity.setIsbn("0-0000-0000-0");
//        notExistingEntity.setGenre(Book.Genre.ART);
//        notExistingEntity.setCover(Book.Cover.HARD);
//        notExistingEntity.setPages(100);
//        notExistingEntity.setRating(BigDecimal.ONE);
//
//        entityForCreation = new Book();
//        entityForCreation.setTitle("Existing title");
//        entityForCreation.setAuthor("Existing author");
//        entityForCreation.setPrice(BigDecimal.TEN);
//        entityForCreation.setIsbn("0-0000-0000-0");
//        entityForCreation.setGenre(Book.Genre.ART);
//        entityForCreation.setCover(Book.Cover.HARD);
//        entityForCreation.setPages(100);
//        entityForCreation.setRating(BigDecimal.ONE);
//    }
//
//    private static Stream<Arguments> provideValidSearchInput() {
//        return Stream.of(Arguments.of(EXISTING_TITLE),
//                Arguments.of(EXISTING_PARTIAL_TITLE),
//                Arguments.of(EXISTING_AUTHOR),
//                Arguments.of(EXISTING_PARTIAL_AUTHOR));
//    }
//
//    private static Stream<Arguments> provideInvalidSearchInput() {
//        return Stream.of(Arguments.of(NOT_EXISTING_TITLE),
//                Arguments.of(NOT_EXISTING_PARTIAL_TITLE),
//                Arguments.of(NOT_EXISTING_AUTHOR),
//                Arguments.of(NOT_EXISTING_PARTIAL_AUTHOR));
//    }
//
//    private static Stream<Arguments> provideValidAuthors() {
//        return Stream.of(Arguments.of(EXISTING_AUTHOR),
//                Arguments.of("J.K. Rowling"));
//    }
//
//    private static Stream<Arguments> provideInvalidAuthors() {
//        return Stream.of(Arguments.of(NOT_EXISTING_AUTHOR),
//                Arguments.of("Gorky"));
//    }
//
//    @BeforeEach
//    void setUp() {
//        reset(bookDaoMock);
//    }
//
//    @Test
//    void getByIdPositiveTest() {
//        when(bookDaoMock.getById(EXISTING_ID)).thenReturn(existingEntity);
//        Book existingBook = bookRepo.getById(EXISTING_ID);
//        assertEquals(existingBook, existingEntity);
//    }
//
//    @Test
//    void getByIdNegativeTest() {
//        when(bookDaoMock.getById(NOT_EXISTING_ID)).thenReturn(null);
//        Book notExisting = bookRepo.getById(NOT_EXISTING_ID);
//        assertNull(notExisting);
//    }
//
//    @Test
//    void getByIsbnPositiveTest() {
//        when(bookDaoMock.getByIsbn(EXISTING_ISBN)).thenReturn(existingEntity);
//        Book existingBook = bookRepo.getByIsbn(EXISTING_ISBN);
//        assertEquals(existingBook, existingEntity);
//    }
//
//    @Test
//    void getByIsbnNegativeTest() {
//        when(bookDaoMock.getByIsbn(NOT_EXISTING_ISBN)).thenReturn(null);
//        Book notExisting = bookRepo.getByIsbn(NOT_EXISTING_ISBN);
//        assertNull(notExisting);
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideValidSearchInput")
//    void searchPositiveTest(String searchInput) {
//        when(bookDaoMock.search(searchInput)).thenReturn(existingEntities);
//        List<Book> existingBookList = bookRepo.search(searchInput);
//        assertEquals(existingBookList, existingEntities.stream().toList());
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideInvalidSearchInput")
//    void searchNegativeTest(String searchInput) {
//        when(bookDaoMock.search(searchInput)).thenReturn(new ArrayList<>());
//        List<Book> emptyList = bookRepo.search(searchInput);
//        assertEquals(emptyList, new ArrayList<>());
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideValidAuthors")
//    void getByAuthorPositiveTest(String author) {
//        when(bookDaoMock.getByAuthor(author, LIMIT, OFFSET)).thenReturn(existingEntities);
//        List<Book> existingByAuthor = bookRepo.getByAuthor(author, LIMIT, OFFSET);
//        assertEquals(existingByAuthor, existingEntities.stream().toList());
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideInvalidAuthors")
//    void getByAuthorNegativeTest(String author) {
//        when(bookDaoMock.getByAuthor(author, LIMIT, OFFSET)).thenReturn(new ArrayList<>());
//        List<Book> emptyList = bookRepo.getByAuthor(author, LIMIT, OFFSET);
//        assertEquals(emptyList, new ArrayList<>());
//    }
//
//    @Test
//    void getAllPositiveTest() {
//            when(bookDaoMock.getAll(LIMIT, OFFSET)).thenReturn(existingEntities);
//            List<Book> existingByAuthor = bookRepo.getAll(LIMIT, OFFSET);
//            assertEquals(existingByAuthor, existingEntities.stream().toList());
//    }
//
//    @Test
//    void getAllNegativeTest() {
//        when(bookDaoMock.getAll(LIMIT, OFFSET)).thenReturn(new ArrayList<>());
//        List<Book> emptyList = bookRepo.getAll(LIMIT, OFFSET);
//        assertEquals(emptyList, new ArrayList<>());
//    }
//
//    @Test
//    void createPositiveTest() {
//        when(bookDaoMock.create(entityForCreation)).thenReturn(expectedEntity);
//        Book created = bookRepo.create(entityForCreation);
//        assertEquals(created, expectedEntity);
//    }
//
//    @Test
//    void createNegativeTest() {
//        when(bookDaoMock.create(existingEntity)).thenThrow(AppException.class);
//        assertThrows(AppException.class, () -> bookRepo.create(existingEntity));
//    }
//
//    @Test
//    void updatePositiveTest() {
//        when(bookDaoMock.update(existingEntity)).thenReturn(expectedEntity);
//        Book updated = bookRepo.update(existingEntity);
//        assertEquals(updated, expectedEntity);
//    }
//
//    @Test
//    void updateNegativeTest() {
//        when(bookDaoMock.update(notExistingEntity)).thenThrow(AppException.class);
//        assertThrows(AppException.class, () -> bookRepo.update(notExistingEntity));
//    }
//
//    @Test
//    void deleteByIdPositiveTest() {
//        when(bookDaoMock.deleteById(EXISTING_ID)).thenReturn(true);
//        assertTrue(bookRepo.deleteById(EXISTING_ID));
//    }
//
//    @Test
//    void deleteByIdNegativeTest() {
//        when(bookDaoMock.deleteById(NOT_EXISTING_ID)).thenReturn(false);
//        assertFalse(bookRepo.deleteById(NOT_EXISTING_ID));
//    }
//}