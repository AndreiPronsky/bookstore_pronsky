package online.javaclass.bookstore.data.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.MessageManager;
import online.javaclass.bookstore.data.connection.DataBaseManager;
import online.javaclass.bookstore.data.dao.BookDao;
import online.javaclass.bookstore.data.dto.BookDto;
import online.javaclass.bookstore.exceptions.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {

    private static final String CREATE_BOOK = "INSERT INTO books (title, author, isbn, genre_id, cover_id, pages, price, rating) " +
            "VALUES (?, ?, ?, (SELECT g.id FROM genres g WHERE g.name = ?), " +
            "(SELECT c.id FROM covers c WHERE c.name = ?), ?, ?, ?)";
    private static final String UPDATE_BOOK = "UPDATE books SET title = ?, author = ?, isbn = ?, " +
            "genre_id = (SELECT g.id FROM genres g WHERE g.name = ?), " +
            "cover_id = (SELECT c.id FROM covers c WHERE c.name = ?), pages = ?, price = ?, rating = ? WHERE id = ?";
    private static final String FIND_BOOK_BY_ID = "SELECT b.id, b.title, b.author, b.isbn, g.name AS genre, " +
            "c.name AS cover, b.pages, b.price, b.rating FROM books b " +
            "JOIN genres g ON b.genre_id = g.id " +
            "JOIN covers c on b.cover_id = c.id WHERE b.id = ?";
    private static final String FIND_BOOK_BY_ISBN = "SELECT b.id, b.title, b.author, b.isbn, g.name AS genre, " +
            "c.name AS cover, b.pages, b.price, b.rating FROM books b " +
            "JOIN genres g ON b.genre_id = g.id " +
            "JOIN covers c ON b.cover_id = c.id WHERE isbn = ?";
    private static final String FIND_ALL_BOOKS = "SELECT b.id, b.title, b.author, b.isbn, g.name AS genre, " +
            "c.name AS cover, b.pages, b.price, b.rating FROM books b " +
            "JOIN genres g ON b.genre_id = g.id " +
            "JOIN covers c on b.cover_id = c.id";
    private static final String FIND_ALL_BOOKS_PAGED = "SELECT b.id, b.title, b.author, b.isbn, g.name AS genre, " +
            "c.name AS cover, b.pages, b.price, b.rating FROM books b " +
            "JOIN genres g ON b.genre_id = g.id " +
            "JOIN covers c on b.cover_id = c.id " +
            "ORDER BY b.id LIMIT ? OFFSET ? ";
    private static final String FIND_BOOKS_BY_AUTHOR = "SELECT b.id, b.title, b.author, b.isbn, g.name AS genre, " +
            "c.name AS cover, b.pages, b.price, b.rating FROM books b" +
            "JOIN genres g ON b.genre_id = g.id " +
            "JOIN covers c ON b.cover_id = c.id WHERE author = ?";

    private static final String FIND_BOOKS_BY_AUTHOR_PAGED = "SELECT b.id, b.title, b.author, b.isbn, g.name AS genre, " +
            "c.name AS cover, b.pages, b.price, b.rating FROM books b" +
            "JOIN genres g ON b.genre_id = g.id " +
            "JOIN covers c ON b.cover_id = c.id WHERE author = ?" +
            "ORDER BY b.id LIMIT ? OFFSET ?";

    private static final String SEARCH = "SELECT b.id, b.title, b.author, b.isbn, g.name AS genre," +
            "c.name AS cover, b.pages, b.price, b.rating FROM books b " +
            "JOIN genres g ON b.genre_id = g.id " +
            "JOIN covers c ON b.cover_id = c.id WHERE b.title LIKE ? OR b.author LIKE ?";
    private static final String DELETE_BOOK_BY_ID = "DELETE FROM books WHERE id = ?";
    private static final String COUNT_BOOKS = "SELECT count(*) FROM books";
    private static final String COL_ID = "id";
    private static final String COL_TITLE = "title";
    private static final String COL_AUTHOR = "author";
    private static final String COL_ISBN = "isbn";
    private static final String COL_GENRE = "genre";
    private static final String COL_COVER = "cover";
    private static final String COL_PAGES = "pages";
    private static final String COL_PRICE = "price";
    private static final String COL_RATING = "rating";
    private final DataBaseManager dataBaseManager;
    private final MessageManager messageManager = MessageManager.INSTANCE;

    @Override
    public List<BookDto> search(String input) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SEARCH)) {
            String reformattedForSearchInput = "%" + input + "%";
            statement.setString(1, reformattedForSearchInput);
            statement.setString(2, reformattedForSearchInput);
            return createBookList(statement);
        } catch (SQLException e) {
            log.error(e.getMessage() + e);
            throw new UnableToFindException(messageManager.getMessage("books.unable_to_find_containing")
                    + " " + input + messageManager.getMessage("in_title"));
        }
    }

    public Long count() {
        try (Connection connection = dataBaseManager.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(COUNT_BOOKS);
            log.debug("DB query completed");
            Long count = null;
            if (result.next()) {
                count = result.getLong("count");
            }
            return count;
        } catch (SQLException e) {
            log.error(e.getMessage() + e);
            throw new AppException(messageManager.getMessage("count_failed"));
        }
    }

    public BookDto create(BookDto book) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_BOOK, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatementForCreate(book, statement);
            statement.executeUpdate();
            log.debug("DB query completed");
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                log.debug("Created book with id" + result.getLong(COL_ID));
                book.setId(result.getLong(COL_ID));
            }
            return book;
        } catch (SQLException e) {
            log.error(e.getMessage() + e);
            throw new UnableToCreateException(messageManager.getMessage("book.unable_to_create"));
        }
    }

    public BookDto update(BookDto book) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BOOK)) {
            prepareStatementForUpdate(book, statement);
            statement.executeUpdate();
            log.debug("DB query completed");
            return book;
        } catch (SQLException e) {
            log.error(e.getMessage() + e);
            throw new UnableToUpdateException(messageManager.getMessage("book.unable_to_update"));
        }
    }

    public BookDto getById(Long id) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BOOK_BY_ID)) {
            statement.setLong(1, id);
            return extractedFromStatement(statement);
        } catch (SQLException e) {
            log.error(e.getMessage() + e);
            throw new UnableToFindException(messageManager.getMessage("book.unable_to_find_id"));
        }
    }

    public BookDto getByIsbn(String isbn) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BOOK_BY_ISBN)) {
            statement.setString(1, isbn);
            return extractedFromStatement(statement);
        } catch (SQLException e) {
            log.error(e.getMessage() + e);
            throw new UnableToFindException(messageManager.getMessage("book.unable_to_find_isbn"));
        }
    }

    public List<BookDto> getByAuthor(String author) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BOOKS_BY_AUTHOR)) {
            statement.setString(1, author);
            return createBookList(statement);
        } catch (SQLException e) {
            log.error(e.getMessage() + e);
            throw new UnableToFindException(messageManager.getMessage("books.unable_to_find_author"));
        }
    }

    public List<BookDto> getByAuthor(String author, int limit, int offset) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BOOKS_BY_AUTHOR_PAGED)) {
            statement.setString(1, author);
            statement.setInt(2, limit);
            statement.setInt(3, offset);
            return createBookList(statement);
        } catch (SQLException e) {
            log.error(e.getMessage() + e);
            throw new UnableToFindException(messageManager.getMessage("books.unable_to_find_author"));
        }
    }

    public List<BookDto> getAll() {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_BOOKS)) {
            return createBookList(statement);
        } catch (SQLException e) {
            log.error(e.getMessage() + e);
            throw new UnableToFindException(messageManager.getMessage("books.unable_to_find"));
        }
    }

    @Override
    public List<BookDto> getAll(int limit, int offset) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_BOOKS_PAGED)) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            return createBookList(statement);
        } catch (SQLException e) {
            log.error(e.getMessage() + e);
            throw new UnableToFindException(messageManager.getMessage("books.unable_to_find"));
        }
    }

    public boolean deleteById(Long id) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BOOK_BY_ID)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            log.debug("DB query completed");
            return affectedRows == 1;
        } catch (SQLException e) {
            log.error(e.getMessage() + e);
            throw new UnableToDeleteException(messageManager.getMessage("book.unable_to_delete") + id);
        }
    }

    private BookDto extractedFromStatement(PreparedStatement statement) throws SQLException {
        ResultSet result = statement.executeQuery();
        log.debug("DB query completed");
        BookDto book = null;
        if (result.next()) {
             book = new BookDto();
            setParameters(book, result);
        }
        return book;
    }

    private List<BookDto> createBookList(PreparedStatement statement) throws SQLException {
        ResultSet result = statement.executeQuery();
        log.debug("DB query completed");
        List<BookDto> books = new ArrayList<>();
        while (result.next()) {
            BookDto book = new BookDto();
            setParameters(book, result);
            books.add(book);
        }
            return books;
    }

    private void setParameters(BookDto book, ResultSet result) throws SQLException {
        book.setId(result.getLong(COL_ID));
        book.setTitle(result.getString(COL_TITLE));
        book.setAuthor(result.getString(COL_AUTHOR));
        book.setIsbn(result.getString(COL_ISBN));
        book.setGenre(BookDto.Genre.valueOf(result.getString(COL_GENRE)));
        book.setCover(BookDto.Cover.valueOf(result.getString(COL_COVER)));
        book.setPages(result.getInt(COL_PAGES));
        book.setPrice(result.getBigDecimal(COL_PRICE));
        book.setRating(result.getBigDecimal(COL_RATING));
    }

    private void prepareStatementForCreate(BookDto book, PreparedStatement statement) throws SQLException {
        statement.setString(1, book.getTitle());
        statement.setString(2, book.getAuthor());
        statement.setString(3, book.getIsbn());
        statement.setString(4, book.getGenre().toString());
        statement.setString(5, book.getCover().toString());
        statement.setInt(6, book.getPages());
        statement.setBigDecimal(7, book.getPrice());
        statement.setBigDecimal(8, book.getRating());
    }

    private void prepareStatementForUpdate(BookDto book, PreparedStatement statement) throws SQLException {
        statement.setString(1, book.getTitle());
        statement.setString(2, book.getAuthor());
        statement.setString(3, book.getIsbn());
        statement.setString(4, book.getGenre().toString());
        statement.setString(5, book.getCover().toString());
        statement.setInt(6, book.getPages());
        statement.setBigDecimal(7, book.getPrice());
        statement.setBigDecimal(8, book.getRating());
        statement.setLong(9, book.getId());
    }
}