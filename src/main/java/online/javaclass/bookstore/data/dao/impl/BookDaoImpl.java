package online.javaclass.bookstore.data.dao.impl;

import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.data.connection.DataBaseManager;
import online.javaclass.bookstore.data.dao.BookDao;
import online.javaclass.bookstore.data.dto.BookDto;
import online.javaclass.bookstore.service.exceptions.UnableToCreateException;
import online.javaclass.bookstore.service.exceptions.UnableToDeleteException;
import online.javaclass.bookstore.service.exceptions.UnableToFindException;
import online.javaclass.bookstore.service.exceptions.UnableToUpdateException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
@Log4j2
public class BookDaoImpl implements BookDao {

    private static final String CREATE_BOOK = "INSERT INTO books (title, author, isbn, genre, cover, pages, price, rating) " +
            "VALUES (?, ?, ?, (SELECT genre_id FROM genres WHERE genre_id = ?), " +
            "(SELECT covers FROM covers WHERE covers_id = ?), ?, ?, ?)";
    private static final String UPDATE_BOOK = "UPDATE books SET title = ?, author = ?, isbn = ?, genre = ?," +
            " cover = ?, pages = ?, price = ?, rating = ? WHERE book_id = ?";
    private static final String FIND_BOOK_BY_ID = "SELECT book_id, title, author, isbn, genre, " +
            "cover, pages, price, rating FROM books " +
            "JOIN genres ON books.genre = genres.genres_id " +
            "JOIN covers on books.cover = covers.covers_id WHERE book_id = ?";
    private static final String FIND_BOOK_BY_ISBN = "SELECT book_id, title, author, isbn, genre, " +
            "cover, pages, price, rating FROM books " +
            "JOIN genres ON books.genre = genres.genres_id " +
            "JOIN covers on books.cover = covers.covers_id WHERE isbn = ?";
    private static final String FIND_ALL_BOOKS = "SELECT book_id, title, author, isbn, genre, cover, " +
            "pages, price, rating FROM books " +
            "JOIN genres ON books.genre = genres.genres_id " +
            "JOIN covers on books.cover = covers.covers_id";
    private static final String FIND_BOOKS_BY_AUTHOR = "SELECT book_id, title, author, isbn, genre, " +
            "cover, pages, price, rating FROM books " +
            "JOIN genres ON books.genre = genres.genres_id " +
            "JOIN covers on books.cover = covers.covers_id WHERE author = ?";
    private static final String DELETE_BOOK_BY_ID = "DELETE FROM books WHERE book_id = ?";
    private static final String COL_BOOK_ID = "book_id";
    private static final String COL_TITLE = "title";
    private static final String COL_AUTHOR = "author";
    private static final String COL_ISBN = "isbn";
    private static final String COL_GENRE = "genre";
    private static final String COL_COVER = "cover";
    private static final String COL_PAGES = "pages";
    private static final String COL_PRICE = "price";
    private static final String COL_RATING = "rating";

    private final DataBaseManager dataBaseManager;

    public BookDaoImpl(DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
    }

    public BookDto create(BookDto book) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_BOOK, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatementForCreate(book, statement);
            statement.executeUpdate();
            log.debug("DB query completed");
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                book.setId(result.getLong(COL_BOOK_ID));
            }
            log.debug("Created book with id" + result.getLong(COL_BOOK_ID));
            return findById(result.getLong(COL_BOOK_ID));
        } catch (Exception e) {
            throw new UnableToCreateException("Creation failed! " + book, e);
        }
    }

    public BookDto update(BookDto book) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BOOK)) {
            prepareStatementForUpdate(book, statement);
            statement.executeUpdate();
            log.debug("DB query completed");
            return findById(book.getId());
        } catch (SQLException e) {
            throw new UnableToUpdateException("Update failed! " + book, e);
        }
    }

    public BookDto findById(Long id) {
        BookDto book = new BookDto();
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BOOK_BY_ID)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                setParameters(book, result);
            }
            log.debug("DB query completed");
            return book;
        } catch (SQLException e) {
            throw new UnableToFindException("No such book found! " + book, e);
        }
    }

    public BookDto findByIsbn(String isbn) {
        BookDto book = new BookDto();
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BOOK_BY_ISBN)) {
            statement.setString(1, isbn);
            ResultSet result = statement.executeQuery();
            log.debug("DB query completed");
            setParameters(book, result);
            return book;
        } catch (SQLException e) {
            throw new UnableToFindException("No such book found! " + book, e);
        }
    }

    public List<BookDto> findByAuthor(String author) {
        List<BookDto> books = new ArrayList<>();
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BOOKS_BY_AUTHOR)) {
            statement.setString(1, author);
            ResultSet result = statement.executeQuery();
            log.debug("DB query completed");
            while (result.next()) {
                long id = result.getLong(COL_BOOK_ID);
                BookDto book = findById(id);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException("No books by " + author + " found", e);
        }
    }

    public List<BookDto> findAll() {
        List<BookDto> books = new ArrayList<>();
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_BOOKS)) {
            ResultSet result = statement.executeQuery();
            log.debug("DB query completed");
            while (result.next()) {
                long id = result.getLong(COL_BOOK_ID);
                BookDto book = findById(id);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException("No books found", e);
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
            throw new UnableToDeleteException("Unable to delete book with id " + id, e);
        }
    }

    private void setParameters(BookDto book, ResultSet result) throws SQLException {
            while (result.next()) {
                book.setId(result.getLong(COL_BOOK_ID));
                book.setTitle(result.getString(COL_TITLE));
                book.setAuthor(result.getString(COL_AUTHOR));
                book.setIsbn(result.getString(COL_ISBN));
                book.setGenre(BookDto.Genre.values()[(result.getInt(COL_GENRE))-1]);
                book.setCover(BookDto.Cover.valueOf(result.getString(COL_COVER)));
                book.setPages(result.getInt(COL_PAGES));
                book.setPrice(result.getBigDecimal(COL_PRICE));
                book.setRating(result.getBigDecimal(COL_RATING));
            }
    }

    private void prepareStatementForCreate(BookDto book, PreparedStatement statement) throws SQLException {
        statement.setString(1, book.getTitle());
        statement.setString(2, book.getAuthor());
        statement.setString(3, book.getIsbn());
        statement.setInt(4, book.getGenre().ordinal());
        statement.setInt(5, book.getCover().ordinal());
        statement.setInt(6, book.getPages());
        statement.setBigDecimal(7, book.getPrice());
        statement.setBigDecimal(8, book.getRating());
    }

    private void prepareStatementForUpdate(BookDto book, PreparedStatement statement) throws SQLException {
        statement.setString(1, book.getTitle());
        statement.setString(2, book.getAuthor());
        statement.setString(3, book.getIsbn());
        statement.setInt(4, book.getGenre().ordinal());
        statement.setInt(5, book.getCover().ordinal());
        statement.setInt(6, book.getPages());
        statement.setBigDecimal(7, book.getPrice());
        statement.setBigDecimal(8, book.getRating());
        statement.setLong(9, book.getId());
    }
}