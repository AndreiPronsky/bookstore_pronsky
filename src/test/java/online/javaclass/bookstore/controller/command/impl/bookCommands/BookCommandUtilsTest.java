//package online.javaclass.bookstore.controller.command.impl.bookCommands;
//
//import online.javaclass.bookstore.platform.MessageManager;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//import java.util.stream.Stream;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertNull;
//
//class BookCommandUtilsTest {
//    private static BookCommandUtils utils;
//    private static List<String> messages;
//    private static MessageManager messageManager;
//
//    @BeforeAll
//    static void beforeAll() {
//        utils = new BookCommandUtils();
//        messages = new ArrayList<>();
//        messageManager = MessageManager.INSTANCE;
//        messageManager.setLocale(Locale.getDefault().getLanguage());
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideValidTitle")
//    void validateAndReformatTitlePositiveTest(String rawTitle) {
//        String title = utils.validateAndReformatTitle(rawTitle, messages);
//        assertNotNull(title);
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideInvalidTitle")
//    void validateAndReformatTitleNegativeTest(String rawTitle) {
//        String title = utils.validateAndReformatTitle(rawTitle, messages);
//        assertNull(title);
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideValidAuthor")
//    void validateAndReformatAuthorPositiveTest(String rawAuthor) {
//        String author = utils.validateAndReformatTitle(rawAuthor, messages);
//        assertNotNull(author);
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideInvalidAuthor")
//    void validateAndReformatAuthorNegativeTest(String rawAuthor) {
//        String author = utils.validateAndReformatAuthor(rawAuthor, messages);
//        assertNull(author);
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideValidIsbn")
//    void validateAndReformatIsbnPositiveTest(String rawIsbn) {
//        String isbn = utils.validateAndReformatIsbn(rawIsbn, messages);
//        assertNotNull(isbn);
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideInvalidIsbn")
//    void validateAndReformatIsbnNegativeTest(String rawIsbn) {
//        String isbn = utils.validateAndReformatIsbn(rawIsbn, messages);
//        assertNull(isbn);
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideValidGenre")
//    void validateAndReformatGenrePositiveTest(String rawGenre) {
//        String genre = utils.validateAndReformatGenre(rawGenre, messages);
//        assertNotNull(genre);
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideInvalidGenre")
//    void validateAndReformatGenreNegativeTest(String rawGenre) {
//        String genre = utils.validateAndReformatGenre(rawGenre, messages);
//        assertNull(genre);
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideValidCover")
//    void validateAndReformatCoverPositiveTest(String rawCover) {
//        String cover = utils.validateAndReformatGenre(rawCover, messages);
//        assertNotNull(cover);
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideInvalidCover")
//    void validateAndReformatCoverNegativeTest(String rawCover) {
//        String cover = utils.validateAndReformatGenre(rawCover, messages);
//        assertNull(cover);
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideValidRating")
//    void validateAndReformatRatingPositiveTest(String rawRating) {
//        String rating = utils.validateAndReformatRating(rawRating, messages);
//        assertNotNull(rating);
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideInvalidRating")
//    void validateAndReformatRatingNegativeTest(String rawRating) {
//        String rating = utils.validateAndReformatRating(rawRating, messages);
//        assertNull(rating);
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideValidPages")
//    void validateAndReformatPagesPositiveTest(String rawPages) {
//        String pages = utils.validateAndReformatPages(rawPages, messages);
//        assertNotNull(pages);
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideInvalidPages")
//    void validateAndReformatPagesNegativeTest(String rawPages) {
//        String pages = utils.validateAndReformatPages(rawPages, messages);
//        assertNull(pages);
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideValidPrice")
//    void validateAndReformatPricePositiveTest(String rawPrice) {
//        String price = utils.validateAndReformatPrice(rawPrice, messages);
//        assertNotNull(price);
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideInvalidPrice")
//    void validateAndReformatPriceNegativeTest(String rawPrice) {
//        String price = utils.validateAndReformatPrice(rawPrice, messages);
//        assertNull(price);
//    }
//
//    private static Stream<Arguments> provideValidTitle() {
//        return Stream.of(Arguments.of("Title"),
//                Arguments.of("   Title   "),
//                Arguments.of("123"),
//                Arguments.of("123.4"),
//                Arguments.of("Title    4"),
//                Arguments.of("Dr. Marten's"),
//                Arguments.of("I'm alive"),
//                Arguments.of(".com"),
//                Arguments.of("Swan, cancer and pike"),
//                Arguments.of("Book #5"),
//                Arguments.of("non Capital first Letter"));
//    }
//
//    private static Stream<Arguments> provideInvalidTitle() {
//        return Stream.of(Arguments.of(""),
//                Arguments.of("..,"),
//                Arguments.of((Object) null),
//                Arguments.of("   "));
//    }
//
//    private static Stream<Arguments> provideValidAuthor() {
//        return Stream.of(Arguments.of("Author"),
//                Arguments.of("  Author   "),
//                Arguments.of("Author    4"),
//                Arguments.of("Dr. Marten's"),
//                Arguments.of("Jean-Paul Sartre"),
//                Arguments.of("Simone de Beauvoir"),
//                Arguments.of("J.K.Rowling"),
//                Arguments.of("Ursula von der Leyen"));
//    }
//
//    private static Stream<Arguments> provideInvalidAuthor() {
//        return Stream.of(Arguments.of("..."),
//                Arguments.of((Object) null),
//                Arguments.of(""),
//                Arguments.of("    "));
//    }
//
//    private static Stream<Arguments> provideValidIsbn() {
//        return Stream.of(Arguments.of("978-3-16-148410-0"),
//                Arguments.of("0-1234-5678-9"),
//                Arguments.of("   0-1234-5678-9  "),
//                Arguments.of(" 0 - 1234 - 5678 - 9"));
//    }
//
//    private static Stream<Arguments> provideInvalidIsbn() {
//        return Stream.of(Arguments.of("dgfd"),
//                Arguments.of("0--123456789-"),
//                Arguments.of(",1-2345.6789-0"),
//                Arguments.of((Object) null));
//
//    }
//
//    private static Stream<Arguments> provideValidGenre() {
//        return Stream.of(Arguments.of("ART"),
//                Arguments.of("art"),
//                Arguments.of("Art"),
//                Arguments.of(" ART   "));
//    }
//
//    private static Stream<Arguments> provideInvalidGenre() {
//        return Stream.of(Arguments.of("MATHEMATICS"),
//                Arguments.of("123"),
//                Arguments.of(".ART"),
//                Arguments.of((Object) null));
//    }
//
//    private static Stream<Arguments> provideValidCover() {
//        return Stream.of(Arguments.of("ART"),
//                Arguments.of("art"),
//                Arguments.of("Art"),
//                Arguments.of(" ART   "));
//    }
//
//    private static Stream<Arguments> provideInvalidCover() {
//        return Stream.of(Arguments.of("SUPERSOFT"),
//                Arguments.of("123"),
//                Arguments.of(".SOFT"),
//                Arguments.of((Object) null));
//    }
//
//    private static Stream<Arguments> provideValidRating() {
//        return Stream.of(Arguments.of("2.5"),
//                Arguments.of("  2.5 "),
//                Arguments.of("2,5"),
//                Arguments.of("02.50"),
//                Arguments.of("02,50"));
//    }
//
//    private static Stream<Arguments> provideInvalidRating() {
//        return Stream.of(Arguments.of("77"),
//                Arguments.of("-2"),
//                Arguments.of("ZERO"));
//    }
//
//    private static Stream<Arguments> provideValidPages() {
//        return Stream.of(Arguments.of("5"),
//                Arguments.of("  5 "),
//                Arguments.of("5 5 "),
//                Arguments.of("055"));
//    }
//
//    private static Stream<Arguments> provideInvalidPages() {
//        return Stream.of(Arguments.of("-55"),
//                Arguments.of("0"),
//                Arguments.of("55.5"),
//                Arguments.of("fifty five"),
//                Arguments.of((Object) null));
//    }
//
//    private static Stream<Arguments> provideValidPrice() {
//        return Stream.of(Arguments.of("55"),
//                Arguments.of("4"),
//                Arguments.of("  55  "),
//                Arguments.of("55.5"),
//                Arguments.of("55,5"),
//                Arguments.of("055.5"));
//    }
//
//    private static Stream<Arguments> provideInvalidPrice() {
//        return Stream.of(Arguments.of("-5"),
//                Arguments.of("0"),
//                Arguments.of("fifty five"),
//                Arguments.of("055.5.5"));
//    }
//}